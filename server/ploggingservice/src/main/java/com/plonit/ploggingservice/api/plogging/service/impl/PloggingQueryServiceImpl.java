package com.plonit.ploggingservice.api.plogging.service.impl;

import com.plonit.ploggingservice.api.plogging.controller.SidoGugunFeignClient;
import com.plonit.ploggingservice.api.plogging.controller.response.*;
import com.plonit.ploggingservice.api.plogging.service.PloggingQueryService;
import com.plonit.ploggingservice.common.enums.Time;
import com.plonit.ploggingservice.common.exception.CustomException;
import com.plonit.ploggingservice.common.util.KakaoPlaceUtils;
import com.plonit.ploggingservice.domain.plogging.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.plonit.ploggingservice.common.exception.ErrorCode.INVALID_PLACE_REQUEST;
import static com.plonit.ploggingservice.common.exception.ErrorCode.PLOGGING_BAD_REQUEST;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PloggingQueryServiceImpl implements PloggingQueryService {

    private final SidoGugunFeignClient sidoGugunFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final KakaoPlaceUtils kakaoPlaceUtils;
    private final PloggingQueryRepository ploggingQueryRepository;
    private final PloggingHelpQueryRepository ploggingHelpQueryRepository;
    private final PloggingPictureQueryRepository ploggingPictureQueryRepository;
    private final LatLongQueryRepository latLongQueryRepository;

    /**
     * 플로깅 기록 일별 조회
     * @param startDay 조회 시작 날짜
     * @param endDay 조회 마지막 날짜
     * @param memberKey 사용자 식별키
     * @return 플로깅 기록 일별 조회
     */
    @Override
    public List<PloggingPeriodRes> findPloggingLogByDay(String startDay, String endDay, Long memberKey) {

        // 조회 시작 날짜 -> LocalDate
        LocalDate startDate = LocalDate.parse(startDay, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 조회 마지막 날짜 -> LocalDate
        LocalDate endDate = LocalDate.parse(endDay, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        return ploggingQueryRepository.findPloggingLogByDay(startDate, endDate, memberKey);
    }

    @Override
    public PloggingLogRes findPloggingLogDetail(Long ploggingId, Long memberKey) {
        
        // ploggingId와 memberKey로 플로깅 정보 가져오기
        FindPloggingLogRes findPloggingLogRes = ploggingQueryRepository.findPloggingLogDetail(ploggingId, memberKey)
                .orElseThrow(() -> new CustomException(PLOGGING_BAD_REQUEST));
        
        PloggingLogRes ploggingLogRes = PloggingLogRes.builder()
                .id(findPloggingLogRes.getId())
                .type(findPloggingLogRes.getType())
                .place(findPloggingLogRes.getPlace())
                .startTime(findPloggingLogRes.getStartTime())
                .endTime(findPloggingLogRes.getEndTime())
                .totalTime(findPloggingLogRes.getTotalTime())
                .distance(findPloggingLogRes.getDistance())
                .calorie(findPloggingLogRes.getCalorie())
                .build();

        // 이미지 넣기
        List<FindPloggingImagesRes> imagesByPloggingId = ploggingPictureQueryRepository.findImagesByPloggingId(ploggingId);

        List<String> images = ploggingLogRes.getImages();
        images = new ArrayList<>();
        for (FindPloggingImagesRes findPloggingImagesRes : imagesByPloggingId) {
            images.add(findPloggingImagesRes.getImage());
        }
        ploggingLogRes.setImages(images);
        
        // 위도, 경도 넣기
        List<FindLatLongRes> latLongByPloggingId = latLongQueryRepository.findLatLongByPloggingId(ploggingId);

        List<PloggingLogRes.Coordinate> coordinates = ploggingLogRes.getCoordinates();
        coordinates = new ArrayList<>();
        
        for (FindLatLongRes findLatLongRes : latLongByPloggingId) {
            coordinates.add(PloggingLogRes.Coordinate.builder()
                    .latitude(findLatLongRes.getLatitude())
                    .longitude(findLatLongRes.getLongitude())
                    .build());
        }
        ploggingLogRes.setCoordinates(coordinates);

        return ploggingLogRes;
    }

    @Override
    public List<PloggingHelpRes> findPloggingHelp(Double latitude, Double longitude) {

        KakaoAddressRes.Address address = kakaoPlaceUtils.getAddress(latitude, longitude);
        if (address == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }

        // 구군 코드 얻어오기
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        SidoGugunCodeRes sidoGugunCodeRes = circuitBreaker.run(
                () -> sidoGugunFeignClient.findSidoGugunCode(address.getRegion_1depth_name(), address.getRegion_2depth_name()).getResultBody(), // 통신하는 서비스
                throwable -> null // 에러 발생시 null 반환
        );

        if (sidoGugunCodeRes == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }

        // 구군 코드로 플로깅 도움 요청 조회
        LocalDate now = LocalDate.now(ZoneId.of(Time.SEOUL.name()));
        return ploggingHelpQueryRepository.findPloggingHelp(now, sidoGugunCodeRes.getGugunCode());
    }
}
