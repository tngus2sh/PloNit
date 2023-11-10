package com.plonit.ploggingservice.common.util;

import com.plonit.ploggingservice.common.config.WebClientConfig;
import com.plonit.ploggingservice.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static com.plonit.ploggingservice.common.exception.ErrorCode.KAKAO_ADDRESS_SERVER_ERROR;

@Component
@RequiredArgsConstructor
public class WebClientUtil {
    
    private final WebClientConfig webClientConfig;

//    public <T> T get(String url, Class<T> responseDtoClass) {
    
    public Mono<String> test(String url) {
        return webClientConfig.webClient().method(HttpMethod.GET)
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new CustomException(KAKAO_ADDRESS_SERVER_ERROR)))
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new CustomException(KAKAO_ADDRESS_SERVER_ERROR)))
                .bodyToMono(String.class);
    }
    
}
