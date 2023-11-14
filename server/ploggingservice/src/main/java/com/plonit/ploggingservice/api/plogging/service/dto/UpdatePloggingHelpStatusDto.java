package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.api.plogging.controller.request.UpdatePloggingHelpStatusReq;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdatePloggingHelpStatusDto {

    private Long memberKey;

    private Long ploggingHelpId;

    private Boolean isActive;

    public static UpdatePloggingHelpStatusDto of(UpdatePloggingHelpStatusReq req, Long memberKey) {
        return UpdatePloggingHelpStatusDto.builder()
                .memberKey(memberKey)
                .ploggingHelpId(req.getPloggingHelpId())
                .isActive(req.getIsActive())
                .build();
    }
}
