package com.plonit.ploggingservice.api.plogging.controller.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CrewpingRecordReq {
    
    // 크루핑 id
    private Long crewpingId;
    
    // 시작 시간
    private LocalDateTime startDate;
    
    // 종료 시간
    private LocalDateTime endDate;
    
    // 장소
    private String place;
    
    // 걸린 시간
    private Long activeTime;
    
}
