package com.plonit.ploggingservice.api.plogging.controller.response;

import com.plonit.ploggingservice.common.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PloggingLogRes {
    
    private Long id;
    
    private Type type;
    
    private String place;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private LocalTime totalTime;
    
    private Double distance;
    
    private Double calorie;
    
    private String image;

    private Coordinate[] coordinates;
    
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class Coordinate {
        private Double latitude;

        private Double longitude;
    }
}
