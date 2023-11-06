package com.plonit.ploggingservice.api.plogging.service.impl;

import com.plonit.ploggingservice.api.plogging.controller.SidoGugunFeignClient;
import com.plonit.ploggingservice.api.plogging.controller.response.*;
import com.plonit.ploggingservice.api.plogging.service.PloggingService;
import com.plonit.ploggingservice.api.plogging.service.dto.EndPloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.HelpPloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.ImagePloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.StartPloggingDto;
import com.plonit.ploggingservice.common.AwsS3Uploader;
import com.plonit.ploggingservice.common.enums.Finished;
import com.plonit.ploggingservice.common.enums.Time;
import com.plonit.ploggingservice.common.enums.Type;
import com.plonit.ploggingservice.common.exception.CustomException;
import com.plonit.ploggingservice.common.util.KakaoPlaceUtils;
import com.plonit.ploggingservice.common.util.RedisUtils;
import com.plonit.ploggingservice.common.util.WebClientUtil;
import com.plonit.ploggingservice.domain.plogging.LatLong;
import com.plonit.ploggingservice.domain.plogging.Plogging;
import com.plonit.ploggingservice.domain.plogging.PloggingHelp;
import com.plonit.ploggingservice.domain.plogging.PloggingPicture;
import com.plonit.ploggingservice.domain.plogging.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.plonit.ploggingservice.common.enums.RedisKey.*;
import static com.plonit.ploggingservice.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PloggingServiceImpl implements PloggingService {
    
    private final SidoGugunFeignClient sidoGugunFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final KakaoPlaceUtils kakaoPlaceUtils;
    private final AwsS3Uploader awsS3Uploader;
    private final RedisUtils redisUtils;
    private final PloggingRepository ploggingRepository;
    private final LatLongRepository latLongRepository;
    private final PloggingHelpRepository ploggingHelpRepository;
    private final PloggingPictureRepository ploggingPictureRepository;



    /**
     * 플로깅 시작시 기록 저장
     * @param dto 플로깅 저장 데이터
     * @return 플로깅 id
     */
    @Transactional
    @Override
    public Long saveStartPlogging(StartPloggingDto dto) {
        
        // 위도, 경도로 위치 구하기
        KakaoAddressRes.RoadAddress roadAddress = kakaoPlaceUtils.getRoadAddress(dto.getLatitude(), dto.getLongitude());
        if (roadAddress == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }
        String place = roadAddress.getAddress_name();

        // 시작 시간 구하기
        LocalDateTime startTime = LocalDateTime.now(ZoneId.of(Time.SEOUL.getText()));
        
        // 오늘 날짜 구하기
        LocalDate today = startTime.toLocalDate();

        Plogging plogging = StartPloggingDto.toEntity(dto.getMemberKey(), dto.getType(), place, startTime, Finished.ACTIVE, today);

        return ploggingRepository.save(plogging).getId();
    }


    /**
     * 플로깅 종료시 나머지 데이터 저장
     * @param dto 나머지 데이터
     * @return 플로깅 id
     */
    @Transactional
    @Override
    public Long saveEndPlogging(EndPloggingDto dto) {
        
        // 끝난 시간 구하기
        LocalDateTime endTime = LocalDateTime.now(ZoneId.of(Time.SEOUL.getText()));
        
        // 플로깅 id로 플로깅 가져오기
        Plogging plogging = ploggingRepository.findById(dto.getPloggingId())
                .orElseThrow(() -> new CustomException(PLOGGING_BAD_REQUEST));

        // 걸린 시간 구하기
        Duration duration = Duration.between(plogging.getStartTime(), endTime);
        Long totalTime = duration.get(ChronoUnit.SECONDS);

        // 플로깅 완료로 변경
        plogging.saveEndPlogging(endTime, totalTime, dto.getDistance(), dto.getCalorie(), dto.getReview(), Finished.FINISHED);

        List<LatLong> latLongs = new ArrayList<>();

        // 위도 경도 값 넣기
        for (EndPloggingDto.Coordinate coord : dto.getCoordinates()) {
            latLongs.add(LatLong.builder()
                            .latitude(coord.getLatitude())
                            .longitude(coord.getLongitude())
                    .build());
        }
        
        latLongRepository.saveAll(latLongs);

        /* 크루핑시에 크루핑 테이블에 내용 저장 */


        /* 랭킹*/
        // 기존에 존재하는 랭킹 파악하기 -> 없다면 새로운 랭킹 생성, 랭킹 있다면 값 업데이트
        Double memberRankValue = redisUtils.getValueInSortedSet(MEMBER_RANK.name(), String.valueOf(dto.getMemberKey()));
        if (memberRankValue == null) {
            redisUtils.setRedisSortedSet(MEMBER_RANK.name(), String.valueOf(dto.getMemberKey()), dto.getDistance());
        } else {
                redisUtils.updateRedisSortedSet(MEMBER_RANK.name(), String.valueOf(dto.getMemberKey()), memberRankValue + dto.getDistance());
        }

        // 크루핑이라면 크루 플로깅 랭킹에 넣기
        if (plogging.getType().equals(Type.CREWPING)) {
            // 크루 누적 랭킹
            Double crewRankValue = redisUtils.getValueInSortedSet(CREW_RANK.name(), String.valueOf(dto.getCrewpingId()));
            if (crewRankValue == null) {
                redisUtils.setRedisSortedSet(CREW_RANK.name(), String.valueOf(dto.getMemberKey()), dto.getDistance());
            } else {
                redisUtils.updateRedisSortedSet(CREW_RANK.name(), String.valueOf(dto.getMemberKey()), crewRankValue + dto.getDistance());
            }


            // 크루 평균 랭킹
            Double crewAvgRankValue = redisUtils.getValueInSortedSet(CREW_AVG_RANK.name(), String.valueOf(dto.getCrewpingId()));
            Double avgDistance = (dto.getDistance() / dto.getPeople());
            if (crewAvgRankValue == null) {
                redisUtils.setRedisSortedSet(CREW_AVG_RANK.name(), String.valueOf(dto.getCrewpingId()), avgDistance);
            } else {
                redisUtils.updateRedisSortedSet(CREW_AVG_RANK.name(), String.valueOf(dto.getCrewpingId()), crewAvgRankValue + avgDistance);
            }
        }

        return plogging.getId();
    }
    
    /**
     * 플로깅 도움 요청 저장
     * @param dto 플로깅 도움 요청 데이터
     * @return 플로깅 도움 식별키
     */
    @Transactional
    @Override
    public Long savePloggingHelp(HelpPloggingDto dto) {

        String imageUrl = null;
        if (dto.getImage() != null) {
            try {
                imageUrl = awsS3Uploader.uploadFile(dto.getImage(), "help/image");
            } catch (IOException e) {
                throw new CustomException(INVALID_FIELDS_REQUEST);
            }
        }

        KakaoAddressRes.RoadAddress roadAddress = kakaoPlaceUtils.getRoadAddress(dto.getLatitude(), dto.getLongitude());
        if (roadAddress == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }
        
        // 구군 코드 얻어오기
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        SidoGugunCodeRes sidoGugunCodeRes = circuitBreaker.run(
                () -> sidoGugunFeignClient.findSidoGugunCode(roadAddress.getRegion_1depth_name(), roadAddress.getRegion_2depth_name())
                        .getResultBody(), // 통신하는 서비스
                throwable -> null // 에러 발생시 null 반환
        );

        if (sidoGugunCodeRes == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }
        
        PloggingHelp ploggingHelp = HelpPloggingDto.toEntity(dto, sidoGugunCodeRes.getGugunCode(), roadAddress.getAddress_name(), imageUrl);
        return ploggingHelpRepository.save(ploggingHelp).getId();
    }
    
    @Transactional
    @Override
    public String savePloggingImage(ImagePloggingDto dto) {
        
        // 플로깅 id 있는지 확인
        Plogging plogging = ploggingRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(PLOGGING_BAD_REQUEST));

        // S3에 등록/**/
        String imageUrl = null;
        if (dto.getImage() != null) {
            try {
                imageUrl = awsS3Uploader.uploadFile(dto.getImage(), "picture/image");
            } catch (IOException e) {
                throw new CustomException(INVALID_FIELDS_REQUEST);
            }
        }
        
        // 플로깅 이미지 저장
        PloggingPicture ploggingPicture = ImagePloggingDto.toEntity(dto.getId(), plogging, imageUrl);
        ploggingPictureRepository.save(ploggingPicture);

        return imageUrl;
    }


}
