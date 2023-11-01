package com.plonit.apigatewayservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsConfig implements WebFluxConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOrigins("http://localhost:3000")
                .allowedOrigins("http://localhost")
                .allowedOrigins("https://k9c207.p.ssafy.io")
                .allowedHeaders("*")
                .allowedMethods("*")
                .exposedHeaders("*")
                .allowedOriginPatterns("*")
                .exposedHeaders(HttpHeaders.SET_COOKIE);
    }
}

