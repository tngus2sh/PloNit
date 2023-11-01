package com.plonit.ploggingservice.api.plogging.service.impl;

import com.plonit.ploggingservice.api.plogging.controller.PlonitFeignClient;
import com.plonit.ploggingservice.api.plogging.controller.response.KakaoAddressRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingLogRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingPeriodRes;
import com.plonit.ploggingservice.api.plogging.service.PloggingService;
import com.plonit.ploggingservice.api.plogging.service.dto.EndPloggingDto;
import com.plonit.ploggingservice.api.plogging.service.dto.StartPloggingDto;
import com.plonit.ploggingservice.common.enums.Finished;
import com.plonit.ploggingservice.common.exception.CustomException;
import com.plonit.ploggingservice.common.util.WebClientUtil;
import com.plonit.ploggingservice.domain.plogging.LatLong;
import com.plonit.ploggingservice.domain.plogging.Plogging;
import com.plonit.ploggingservice.domain.plogging.repository.LatLongRepository;
import com.plonit.ploggingservice.domain.plogging.repository.PloggingQueryRepository;
import com.plonit.ploggingservice.domain.plogging.repository.PloggingRepository;
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
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.plonit.ploggingservice.common.exception.ErrorCode.INVALID_PLACE_REQUEST;
import static com.plonit.ploggingservice.common.exception.ErrorCode.PLOGGING_BAD_REQUEST;

@Service
@RequiredArgsConstructor
@Slf4j
public class PloggingServiceImpl implements PloggingService {
    
    private final Environment env;
    private final PlonitFeignClient plonitFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final WebClientUtil webClientUtil;
    private final PloggingRepository ploggingRepository;
    private final PloggingQueryRepository ploggingQueryRepository;
    private final LatLongRepository latLongRepository;
    private String kakaoBaseUrl;
    private String kakaoKey;
    
    @Autowired
    public PloggingServiceImpl(PloggingQueryRepository ploggingQueryRepository, LatLongRepository latLongRepository, PloggingRepository ploggingRepository, WebClientUtil webClientUtil, CircuitBreakerFactory circuitBreakerFactory, PlonitFeignClient plonitFeignClient, Environment env) {
        this.ploggingQueryRepository = ploggingQueryRepository;
        this.latLongRepository = latLongRepository;
        this.ploggingRepository = ploggingRepository;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.webClientUtil = webClientUtil;
        this.plonitFeignClient = plonitFeignClient;
        this.env = env;
        this.kakaoBaseUrl = env.getProperty("kakao.base-url");
        this.kakaoKey = env.getProperty("kakao.apikey");
    }

    /***
     * CircuitBreaker(장애처리) 사용법
     * - microservice 사이의 호출에서 에러가 발생했을 경우 처리
     * - 하나의 서비스가 에러를 발생하면서 다른 서비스가 정지되어버리면 안되니 처리
     * - 여기서는 Error가 발생할 경우 비어있는 리스트를 반환
     */
    public void circuitTest() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitBreaker");

        List<String> testList = circuitBreaker.run(
                () -> plonitFeignClient.getTest().getResultBody(), // 통신하는 서비스
                throwable -> new ArrayList<>() // 에러 발생시 빈 배열 반환
        );
        
        log.info(testList.toString());
    }


    /**
     * 플로깅 시작시 기록 저장
     * @param dto 플로깅 저장 데이터
     * @return 플로깅 id
     */
    @Transactional
    @Override
    public Long saveStartPlogging(StartPloggingDto dto) {
        
        // 위도, 경도로 위치 구하기
        String place = getPlace(dto.getLatitude(), dto.getLongitude());
        if (place == null) {
            throw new CustomException(INVALID_PLACE_REQUEST);
        }

        // 시작 시간 구하기
        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        
        // 오늘 날짜 구하기
        LocalDate today = startTime.toLocalDate();

        return StartPloggingDto.toEntity(dto.getMemberKey(), dto.getType(), place, startTime, Finished.ACTIVE, today)
                .getId();
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
        LocalDateTime endTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        
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
        
        return plogging.getId();
    }

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
        return ploggingQueryRepository.findPloggingLogDetail(ploggingId, memberKey)
                .orElseThrow(() -> new CustomException(PLOGGING_BAD_REQUEST));
    }


    /**
     * 위도, 경도로 지번 주소 얻어옴
     * @param latitude 위도(y)
     * @param longitude 경도(x)
     * @return 지번 주소
     */
    private String getPlace(double latitude, double longitude) {

        // webClient 기본 설정
        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, kakaoKey)
                .build();

        KakaoAddressRes response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("x", longitude)
                        .queryParam("y", latitude)
                        .queryParam("input_cord", "WGS84")
                        .build())
                .retrieve()
                .bodyToMono(KakaoAddressRes.class)
                .block();
        
        // 결과 확인
        log.info(response.toString());
        
        return response.getDocuments().length >=1 ? response.getDocuments()[0].getRoad_address().getAddress_name() : null;
    } 
}
