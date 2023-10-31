package com.plonit.ploggingservice.api.plogging.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@RequiredArgsConstructor
@Schema(description = "플로깅 종료 후 저장 request")
public class EndPloggingRequest {
    
    @Schema(description = "플로깅 id")
    @NotBlank(message = "플로깅 id는 필수 입력값입니다.")
    @Positive
    private Long ploggingId;

    @Schema(description = "좌표")
    private Coordinate[] coordinates;
    
    @Schema(description = "거리")
    @NotBlank(message = "거리는 필수 입력값입니다.")
    @Positive
    private Double distance;
    
    @Schema(description = "칼로리")
    @NotBlank(message = "칼로리는 필수 입력값입니다.")
    @Positive
    private Double calorie;

    @Schema(description = "한줄평")
    private String review;
    
    @Data
    @RequiredArgsConstructor
    public static class Coordinate {
        
        @Schema(description = "위도")
        private Double latitude;
        
        @Schema(description = "경도")
        private Double longitude;
    }
    
}
