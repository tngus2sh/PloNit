package com.plonit.ploggingservice.api.plogging.controller.response;

import com.plonit.ploggingservice.common.enums.Type;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@RequiredArgsConstructor
public class PloggingPeriodRes {
    
    private Long id;
    
    private Type type;
    
    private String place;
    
    private LocalDateTime startTime;
    
    private LocalDateTime endTime;
    
    private LocalTime totalTime;
    
    private Double distance;

    @Builder
    public PloggingPeriodRes(Long id, Type type, String place, LocalDateTime startTime, LocalDateTime endTime, Long totalTime, Double distance) {
        String HH = String.valueOf(totalTime / 60);
        String mm = String.valueOf(totalTime % 60);
        String totalTimeStr = HH + mm;
        LocalTime localTotalTime = LocalTime.parse(totalTimeStr, DateTimeFormatter.ofPattern("HH:mm"));
        
        this.id = id;
        this.type = type;
        this.place = place;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = localTotalTime;
        this.distance = distance;
    }
}
