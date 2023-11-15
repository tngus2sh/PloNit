package com.plonit.ploggingservice.api.plogging.service.impl;

import com.plonit.ploggingservice.api.plogging.controller.*;
import com.plonit.ploggingservice.api.plogging.controller.request.*;
import com.plonit.ploggingservice.api.plogging.controller.response.*;
import com.plonit.ploggingservice.api.plogging.service.PloggingService;
import com.plonit.ploggingservice.api.plogging.service.dto.*;
import com.plonit.ploggingservice.common.AwsS3Uploader;
import com.plonit.ploggingservice.common.enums.Finished;
import com.plonit.ploggingservice.common.enums.Time;
import com.plonit.ploggingservice.common.enums.Type;
import com.plonit.ploggingservice.common.exception.CustomException;
import com.plonit.ploggingservice.common.util.KakaoPlaceUtils;
import com.plonit.ploggingservice.common.util.RedisUtils;
import com.plonit.ploggingservice.domain.plogging.LatLong;
import com.plonit.ploggingservice.domain.plogging.Plogging;
import com.plonit.ploggingservice.domain.plogging.PloggingHelp;
import com.plonit.ploggingservice.domain.plogging.PloggingPicture;
import com.plonit.ploggingservice.domain.plogging.repository.*;
import com.plonit.ploggingservice.common.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private final CrewpingFeignClient crewpingFeignClient;
    private final MemberFeignClient memberFeignClient;
    private final BadgeFeignClient badgeFeignClient;
    private final NotiFeignClient notiFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final KakaoPlaceUtils kakaoPlaceUtils;
    private final AwsS3Uploader awsS3Uploader;
    private final RedisUtils redisUtils;
    private final PloggingRepository ploggingRepository;
    private final LatLongRepository latLongRepository;
    private final PloggingHelpRepository ploggingHelpRepository;
    private final PloggingPictureRepository ploggingPictureRepository;
    private final VolunteerRepository volunteerRepository;
    private final PloggingQueryRepository ploggingQueryRepository;


    /**
     * 플로깅 시작시 기록 저장
     * @param dto 플로깅 저장 데이터
     * @return 플로깅 id
     */
    @Transactional
    @Override
    public Long saveStartPlogging(StartPloggingDto dto) {
        
        // 위도, 경도로 위치 구하기
        KakaoAddressRes.Address address = kakaoPlaceUtils.getAddress(dto.getLatitude(), dto.getLongitude());
        if (address == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }
        String place = address.getAddress_name();

        // 구군 코드 얻어오기
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        SidoGugunCodeRes sidoGugunCodeRes = circuitBreaker.run(
                () -> sidoGugunFeignClient.findSidoGugunCode(address.getRegion_1depth_name(), address.getRegion_2depth_name())
                        .getResultBody(), // 통신하는 서비스
                throwable -> null // 에러 발생시 null 반환
        );

        if (sidoGugunCodeRes == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }

        // 시작 시간 구하기
        LocalDateTime startTime = LocalDateTime.now(ZoneId.of(Time.SEOUL.getText()));
        
        // 오늘 날짜 구하기
        LocalDate today = startTime.toLocalDate();

        Plogging plogging = StartPloggingDto.toEntity(dto.getMemberKey(), dto.getType(), place, sidoGugunCodeRes.getGugunCode(), startTime, Finished.ACTIVE, today);

        Plogging savePlogging = ploggingRepository.save(plogging);

        // 처음 위치 저장
        LatLong latlong = LatLong.builder()
                .plogging(savePlogging)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .build();

        latLongRepository.save(latlong);

        // 크루핑이면서 크루핑장일 때 크루핑 상태 변경 요청
        if (dto.getType().equals(Type.CREWPING)) {
            Boolean isCrewpingMaster = circuitBreaker.run(
                    () -> crewpingFeignClient.isCrewpingMaster(
                                    RequestUtils.getToken(),
                                    dto.getCrewpingId())
                            .getResultBody(),
                    throwable -> null
            );

            if (isCrewpingMaster) {
                circuitBreaker.run(
                        () -> crewpingFeignClient.updateCrewpingStatus(
                                        RequestUtils.getToken(),
                                        UpdateCrewpingStatusReq.builder()
                                                .crewpingId(dto.getCrewpingId())
                                                .build())
                                .getResultBody(),
                        throwable -> null
                );
            }
        }

        return savePlogging.getId();
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
                            .plogging(plogging)
                            .latitude(coord.getLatitude())
                            .longitude(coord.getLongitude())
                    .build());
        }
        
        latLongRepository.saveAll(latLongs);

        /* 크루핑시에 크루핑 테이블에 내용 저장 */

        if (plogging.getType().equals(Type.CREWPING)) {
            CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

            Boolean isCrewpingMaster = circuitBreaker.run(
                    () -> crewpingFeignClient.isCrewpingMaster(
                                    RequestUtils.getToken(),
                                    dto.getCrewpingId())
                            .getResultBody()
                    , throwable -> null
            );

            if (isCrewpingMaster) {
                CrewpingRecordReq crewpingRecordReq = CrewpingRecordReq.builder()
                        .startDate(plogging.getStartTime())
                        .endDate(endTime)
                        .place(plogging.getPlace())
                        .activeTime(totalTime)
                        .build();

                Long crewpingId = circuitBreaker.run(
                        () -> crewpingFeignClient.saveCrewpingRecord(
                                        RequestUtils.getToken(),
                                        crewpingRecordReq)
                                .getResultBody(),
                        throwable -> null // 에러 발생시 null 반환
                );

                if (crewpingId == null) {
                    throw new CustomException(INVALID_CREWPINGID_REQUEST);
                }
            }

        }

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

        /* 개인 배지 부여 */
        // 지금까지 플로깅 횟수와 거리 구하기
        FindCountDistanceRes countDistance = ploggingQueryRepository.findCountDistance(dto.getMemberKey());
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        circuitBreaker.run(
                () -> badgeFeignClient.grantMemberBadge(GrantMemberBadgeReq.builder()
                                .ploggingCount(countDistance.getCount())
                                .distance(countDistance.getDistance())
                                .build())
                        .getResultBody(),
                throwable -> null
        );

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
                imageUrl = awsS3Uploader.uploadFile(dto.getImage(), "plogging/help/image");
            } catch (IOException e) {
                throw new CustomException(INVALID_FIELDS_REQUEST);
            }
        }

        KakaoAddressRes.Address address = kakaoPlaceUtils.getAddress(dto.getLatitude(), dto.getLongitude());
        if (address == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }
        
        // 구군 코드 얻어오기
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        SidoGugunCodeRes sidoGugunCodeRes = circuitBreaker.run(
                () -> sidoGugunFeignClient.findSidoGugunCode(address.getRegion_1depth_name(), address.getRegion_2depth_name())
                        .getResultBody(), // 통신하는 서비스
                throwable -> null // 에러 발생시 null 반환
        );

        if (sidoGugunCodeRes == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }

        // 주변 유저에게 도움 알림 보내기
        Long notiId = circuitBreaker.run(
                () -> notiFeignClient.sendHelpNoti(
                                RequestUtils.getToken(),
                                SendNotiReq.builder()
                                        .gugunCode(sidoGugunCodeRes.getGugunCode())
                                        .build())
                        .getResultBody()
                , throwable -> null
        );

        PloggingHelp ploggingHelp = HelpPloggingDto.toEntity(dto, sidoGugunCodeRes.getGugunCode(), address.getAddress_name(), imageUrl);
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
                imageUrl = awsS3Uploader.uploadFile(dto.getImage(), "plogging/picture/image");
            } catch (IOException e) {
                throw new CustomException(INVALID_FIELDS_REQUEST);
            }
        }
        
        // 플로깅 이미지 저장
        PloggingPicture ploggingPicture = ImagePloggingDto.toEntity(dto.getId(), plogging, imageUrl);
        ploggingPictureRepository.save(ploggingPicture);

        return imageUrl;
    }

    /**
     * 봉사 플로깅 정보 저장
     * @param dto 봉사 플로깅 정보
     * @return 봉사 플로깅 식별키
     */
    @Override
    public Long saveVolunteerData(VolunteerPloggingDto dto) {

        /* 플로깅 id로 플로깅 정보 가져오기 */
        Plogging plogging = ploggingRepository.findById(dto.getPloggingId())
                .orElseThrow(() -> new CustomException(PLOGGING_BAD_REQUEST));

        /* memberKey로 사용자 정보 변경 요청 */
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        UpdateVolunteerInfoReq volunteerInfo = UpdateVolunteerInfoReq.builder()
                .memberKey(dto.getMemberKey())
                .name(dto.getName())
                .phoneNumber(dto.getPhoneNumber())
                .id1365(dto.getId1365())
                .email(dto.getEmail())
                .birth(dto.getBirth())
                .build();

        circuitBreaker.run(
                () -> memberFeignClient.updateVolunteerInfo(volunteerInfo)
                        .getResultBody(),
                throwable -> new CustomException(USER_BAD_REQUEST) // 에러 발생시 해당하는 유저가 없다는 오류 보냄
        );

        /* 정보 저장 */
        volunteerRepository.save(VolunteerPloggingDto.toEntity(plogging));

        return null;
    }

    @Transactional
    @Override
    public Long updatePloggingHelpStatus(UpdatePloggingHelpStatusDto dto) {

        // ploggingHelpId로 해당 도움 가져오기
        PloggingHelp ploggingHelp = ploggingHelpRepository.findByIdAndIsActive(dto.getPloggingHelpId(), true)
                .orElseThrow(() -> new CustomException(PLOGGING_HELP_BAD_REQUEST));

        ploggingHelp.updateIsActive(dto.getIsActive());

        return ploggingHelp.getId();
    }
}
