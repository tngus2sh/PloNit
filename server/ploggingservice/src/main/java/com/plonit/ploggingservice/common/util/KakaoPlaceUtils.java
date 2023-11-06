package com.plonit.ploggingservice.common.util;


import com.plonit.ploggingservice.api.plogging.controller.response.KakaoAddressRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.ws.rs.core.HttpHeaders;

@Component
@Slf4j
public class KakaoPlaceUtils {
    
    private final Environment env;
    private String kakaoBaseUrl;
    private String kakaoKey;

    @Autowired
    public KakaoPlaceUtils(Environment env) {
        this.env = env;
        this.kakaoBaseUrl = env.getProperty("kakao.base-url");
        this.kakaoKey = env.getProperty("kakao.apikey");
    }

    /**
     * 지번 주소 전체 내용 가져오기
     * @param latitude 위도
     * @param longitude 경도
     * @return 주소
     */
    public KakaoAddressRes.RoadAddress getRoadAddress(Double latitude, Double longitude) {

        // webClient 기본 설정
        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoBaseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, kakaoKey)
                .build();

        KakaoAddressRes response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("x", longitude)
                        .queryParam("y", latitude)
                        .queryParam("input_coord", "WGS84")
                        .build())
                .retrieve()
                .bodyToMono(KakaoAddressRes.class)
                .block();

        // 결과 확인
        log.info(response.toString());

        return response.getDocuments().length >=1 ? response.getDocuments()[0].getRoad_address() : null;
    }
}
