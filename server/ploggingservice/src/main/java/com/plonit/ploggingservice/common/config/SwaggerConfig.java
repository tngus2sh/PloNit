package com.plonit.ploggingservice.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("")
                .pathsToMatch("/plogging-service/v1/**")
                .build();
    }
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Plogging API")
                        .description("PloNit 프로젝트 Plogging API 명세서입니다.")
                        .version("v0.0.1"));
    }
}
