package com.plonit.plonitservice.api.crewping.service.dto;

import com.plonit.plonitservice.api.crewping.controller.request.SaveCrewpingRecordReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@Builder(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class SaveCrewpingRecordDto {

    private Long memberId;

    private Long crewpingId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String place;

    private Long activeTime;


    public static SaveCrewpingRecordDto of(Long memberId, SaveCrewpingRecordReq request) {
        return SaveCrewpingRecordDto.builder()
                .memberId(memberId)
                .crewpingId(request.getCrewpingId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .place(request.getPlace())
                .activeTime(request.getActiveTime())
                .build();
    }
}
