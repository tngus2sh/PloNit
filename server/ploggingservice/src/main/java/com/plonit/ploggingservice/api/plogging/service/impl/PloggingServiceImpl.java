package com.plonit.ploggingservice.api.plogging.service.impl;

import com.plonit.ploggingservice.api.plogging.controller.PlonitFeignClient;
import com.plonit.ploggingservice.api.plogging.service.PloggingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PloggingServiceImpl implements PloggingService {
    
    private final Environment env;
    private final PlonitFeignClient plonitFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;

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

}
