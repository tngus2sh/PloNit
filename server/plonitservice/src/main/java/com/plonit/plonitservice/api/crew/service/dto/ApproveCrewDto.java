package com.plonit.plonitservice.api.crew.service.dto;

import com.plonit.plonitservice.api.crew.controller.request.ApproveCrewReq;
import com.plonit.plonitservice.api.crew.controller.request.SaveCrewReq;
import com.plonit.plonitservice.domain.crew.Crew;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
@Builder
public class ApproveCrewDto {

    private Long memberKey;

    private Long crewId;

    private Long crewMemberId;

    private Boolean status;
    public static ApproveCrewDto of (Long memberKey, ApproveCrewReq approveCrewReq) {
        return ApproveCrewDto.builder()
                .memberKey(memberKey)
                .crewId(approveCrewReq.getCrewId())
                .crewMemberId(approveCrewReq.getMemberId())
                .status(approveCrewReq.getStatus())
                .build();
    }

}