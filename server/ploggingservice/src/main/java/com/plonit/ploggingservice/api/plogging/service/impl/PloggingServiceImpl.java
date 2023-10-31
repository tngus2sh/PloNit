package com.plonit.ploggingservice.api.plogging.service.impl;

import com.plonit.ploggingservice.api.plogging.controller.PlonitFeignClient;
import com.plonit.ploggingservice.api.plogging.controller.request.StartPloggingRequest;
import com.plonit.ploggingservice.api.plogging.controller.response.KakaoAddressResponse;
import com.plonit.ploggingservice.api.plogging.service.PloggingService;
import com.plonit.ploggingservice.api.plogging.service.dto.StartPloggingDto;
import com.plonit.ploggingservice.common.enums.Finished;
import com.plonit.ploggingservice.common.util.WebClientUtil;
import com.plonit.ploggingservice.domain.plogging.Plogging;
import com.plonit.ploggingservice.domain.plogging.repository.PloggingRepository;
import com.querydsl.apt.jdo.JDOConfiguration;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import javax.ws.rs.core.HttpHeaders;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PloggingServiceImpl implements PloggingService {
    
    private final Environment env;
    private final PlonitFeignClient plonitFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final WebClientUtil webClientUtil;
    private final PloggingRepository ploggingRepository;
    private String kakaoBaseUrl;
    private String kakaoKey;
    
    @Autowired
    public PloggingServiceImpl(PloggingRepository ploggingRepository, WebClientUtil webClientUtil, CircuitBreakerFactory circuitBreakerFactory, PlonitFeignClient plonitFeignClient, Environment env, String kakaoKey) {
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

    @Transactional
    @Override
    public Long saveStartPlogging(StartPloggingDto dto) {
        
        // 위도, 경도로 위치 구하기
        String place = getPlace(dto.getLatitude(), dto.getLongitude());

        // 시작 시간 구하기
        LocalDateTime startTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        
        // 오늘 날짜 구하기
        LocalDate today = startTime.toLocalDate();

        return Plogging.toFirstEntity(dto.getMemberKey(), dto.getType(), place, startTime, Finished.ACTIVE, today)
                .getId();
    }
    

    /**
     * 위도, 경도로 지번 주소 얻어오는 메소드
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

        KakaoAddressResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("x", longitude)
                        .queryParam("y", latitude)
                        .queryParam("input_cord", "WGS84")
                        .build())
                .retrieve()
                .bodyToMono(KakaoAddressResponse.class)
                .block();
        
        // 결과 확인
        log.info(response.toString());
        
        return response.getDocuments().length >=1 ? response.getDocuments()[0].getRoad_address().getAddress_name() : null;
    } 
}
