package com.plonit.ploggingservice.api.plogging.service.impl;

import com.plonit.ploggingservice.api.plogging.controller.SidoGugunFeignClient;
import com.plonit.ploggingservice.api.plogging.controller.response.*;
import com.plonit.ploggingservice.api.plogging.service.PloggingQueryService;
import com.plonit.ploggingservice.common.enums.Time;
import com.plonit.ploggingservice.common.enums.Type;
import com.plonit.ploggingservice.common.exception.CustomException;
import com.plonit.ploggingservice.common.util.KakaoPlaceUtils;
import com.plonit.ploggingservice.common.util.RequestUtils;
import com.plonit.ploggingservice.domain.plogging.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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
    private final GropQueryRepository gropQueryRepository;

    /**
     * 플로깅 기록 일별 조회
     * @param startDay 조회 시작 날짜
     * @param endDay 조회 마지막 날짜
     * @param memberKey 사용자 식별키
     * @return 플로깅 기록 일별 조회
     */
    @Override
    public List<PloggingPeriodRes> findPloggingLogByDay(String startDay, String endDay, Long memberKey, String type) {

        // 조회 시작 날짜 -> LocalDate
        LocalDate startDate = LocalDate.parse(startDay, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // 조회 마지막 날짜 -> LocalDate
        LocalDate endDate = LocalDate.parse(endDay, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        if (type.equals(Type.IND.name())) {
            return ploggingQueryRepository.findPloggingLogByDayAndType(startDate, endDate, memberKey, Type.IND);
        } else if (type.equals(Type.VOL.name())) {
            return ploggingQueryRepository.findPloggingLogByDayAndType(startDate, endDate, memberKey, Type.VOL);
        } else if (type.equals(Type.CREWPING.name())) {
            return ploggingQueryRepository.findPloggingLogByDayAndType(startDate, endDate, memberKey, Type.CREWPING);
        }
        return ploggingQueryRepository.findPloggingLogByDay(startDate, endDate, memberKey);
    }

    @Override
    public List<PloggingMonthRes> findPloggingLogByMonth(int month, Long memberKey) {

        YearMonth yearMonth = YearMonth.now().withMonth(month);

        // 해당 월의 첫 날짜 구하기
        LocalDate firstDayOfMonth = yearMonth.atDay(1);

        // 해당 월의 마지막 날짜 구하기
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        return ploggingQueryRepository.findPlogginLogByMonth(memberKey, firstDayOfMonth, lastDayOfMonth);
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
                .review(findPloggingLogRes.getReview())
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

    /**
     * 플로깅 도움 요청 조회
     * @param latitude 위도
     * @param longitude 경도
     * @return 해당 구의 활성화된 도움 요청 저장
     */
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
        LocalDate now = LocalDate.now(ZoneId.of(Time.SEOUL.text));
        return ploggingHelpQueryRepository.findPloggingHelp(now, sidoGugunCodeRes.getGugunCode());
    }

    @Override
    public List<UsersRes> findPloggingUsers(Double latitude, Double longitude) {

        // 위도, 경도로 현재 위치 받아오기
        KakaoAddressRes.Address address = kakaoPlaceUtils.getAddress(latitude, longitude);
        if (address == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }

        // 구군 코드 얻어오기
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        SidoGugunCodeRes sidoGugunCodeRes = circuitBreaker.run(
                () -> sidoGugunFeignClient.findSidoGugunCode(address.getRegion_1depth_name(), address.getRegion_2depth_name()).getResultBody(), // 통신하는 서비스
                throwable -> null
        );

        if (sidoGugunCodeRes == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }

        // 구군 코드로 현재 ACTIVE한 사용자들 정보 가져오기
        return ploggingQueryRepository.findNearByUsers(sidoGugunCodeRes.getGugunCode());
    }

    @Override
    public Integer countMemberPlogging() {
        Long memberId = RequestUtils.getMemberId();

        Integer response = ploggingQueryRepository.countPloggingByMemberId(memberId);

        return response;
    }

    @Override
    public HashMap<Long, Long> countCrewPlogging() {
        HashMap<Long, Long> response = gropQueryRepository.countCrewPlogging();
        return response;
    }
}
