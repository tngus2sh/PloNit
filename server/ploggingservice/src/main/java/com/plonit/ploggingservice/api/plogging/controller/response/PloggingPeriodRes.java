package com.plonit.ploggingservice.api.plogging.controller.response;

import com.plonit.ploggingservice.common.enums.Type;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PloggingPeriodRes {
    
    private Long id;
    
    private Type type;
    
    private String place;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private Long totalTime;
    
    private Double distance;

    @Builder
    public PloggingPeriodRes(Long id, Type type, String place, LocalDateTime startTime, LocalDateTime endTime, Long totalTime, Double distance) {
        this.id = id;
        this.type = type;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.distance = distance;
    }
}
