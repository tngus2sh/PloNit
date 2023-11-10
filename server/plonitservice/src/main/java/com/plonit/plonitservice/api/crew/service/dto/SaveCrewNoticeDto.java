package com.plonit.plonitservice.api.crew.service.dto;

import com.plonit.plonitservice.api.crew.controller.request.SaveCrewNoticeReq;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class SaveCrewNoticeDto {

    private Long crewId;

    private String content;

    public static SaveCrewNoticeDto of(SaveCrewNoticeReq saveCrewNoticeReq) {
        return SaveCrewNoticeDto.builder()
                .crewId(saveCrewNoticeReq.getCrewId())
                .content(saveCrewNoticeReq.getContent())
                .build();
    }
}