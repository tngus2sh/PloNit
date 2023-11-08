package com.plonit.plonitservice.api.crew.service.dto;

import com.plonit.plonitservice.api.crew.controller.request.UpdateCrewNoticeReq;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCrewNoticeDto {

    private Long crewId;

    private String content;

    public static UpdateCrewNoticeDto of(UpdateCrewNoticeReq updateCrewNoticeReq) {
        return UpdateCrewNoticeDto.builder()
                .crewId(updateCrewNoticeReq.getCrewId())
                .content(updateCrewNoticeReq.getContent())
                .build();
    }
}