package com.plonit.apigatewayservice.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.reactive.config.CorsRegistry;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//
//import java.util.Arrays;
//
//@Configuration
//public class CorsConfig implements WebFluxConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowCredentials(true)
//                .allowedOrigins("http://localhost:3000", "http://localhost",
//                        "https://k9c207.p.ssafy.io", "http://k9c207.p.ssafy.io",
//                        "http://172.26.5.30", "http://172.17.0.1", "http://172.18.0.1"
//                ,"http://54.180.135.78:80", "http://162.216.149.111", "http://121.178.98.38")
//                .allowedHeaders("*")
//                .allowedMethods(
//                        HttpMethod.GET.name(),
//                        HttpMethod.HEAD.name(),
//                        HttpMethod.POST.name(),
//                        HttpMethod.PUT.name(),
//                        HttpMethod.DELETE.name(),
//                        HttpMethod.OPTIONS.name()
//                )
//                .exposedHeaders("*")
//                .allowedOriginPatterns("*")
//                .exposedHeaders(HttpHeaders.SET_COOKIE);
//    }
//
//    @Bean
//    public CorsWebFilter corsWebFilter() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//        corsConfiguration.addAllowedOrigin("http://127.0.0.1:3000");
//        corsConfiguration.addAllowedOrigin("https://k9c207.p.ssafy.io");
//        corsConfiguration.addAllowedOrigin("http://127.0.0.1:3000");
//        corsConfiguration.addAllowedOrigin("http://172.26.5.30");
//        corsConfiguration.addAllowedOrigin("http://172.17.0.1");
//        corsConfiguration.addAllowedOrigin("http://172.18.0.1");
//        corsConfiguration.addAllowedOrigin("http://54.180.135.78:80");
//        corsConfiguration.addAllowedOrigin("http://162.216.149.111");
//        corsConfiguration.addAllowedOrigin("http://121.178.98.38");
//        corsConfiguration.addAllowedOriginPattern("*");
//        corsConfiguration.addExposedHeader(HttpHeaders.SET_COOKIE);
//        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
//        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsWebFilter(corsConfigurationSource);
//    }
//}
//
