package com.plonit.ploggingservice.api.plogging.controller.response;

import com.plonit.ploggingservice.common.enums.Type;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindPloggingLogRes {

    private Long id;

    private Type type;

    private String place;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalTime totalTime;

    private Double distance;

    private Double calorie;
    
}
