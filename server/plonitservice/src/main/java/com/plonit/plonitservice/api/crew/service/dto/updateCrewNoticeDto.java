package com.plonit.plonitservice.api.crew.service.dto;

import com.plonit.plonitservice.api.crew.controller.request.UpdateCrewNoticeReq;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class updateCrewNoticeDto {

    private Long crewId;

    private String content;

    public static updateCrewNoticeDto of(UpdateCrewNoticeReq updateCrewNoticeReq) {
        return updateCrewNoticeDto.builder()
                .crewId(updateCrewNoticeReq.getCrewId())
                .content(updateCrewNoticeReq.getContent())
                .build();
    }
}