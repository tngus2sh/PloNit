package com.plonit.ploggingservice.api.plogging.controller.response;

import com.plonit.ploggingservice.common.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PloggingLogRes {
    
    private Long id;
    
    private Type type;
    
    private String place;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private Long totalTime;
    
    private Double distance;
    
    private Double calorie;
    
    private String review;
    
    private List<String> images;

    private List<Coordinate> coordinates;
    
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static class Coordinate {
        private Double latitude;

        private Double longitude;
    }
}
