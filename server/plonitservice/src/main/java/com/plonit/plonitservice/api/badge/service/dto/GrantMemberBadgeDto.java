package com.plonit.plonitservice.api.badge.service.dto;

import com.plonit.plonitservice.api.badge.controller.request.GrantMemberBadgeReq;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GrantMemberBadgeDto {

    private Integer ploggingCount;

    private Double distance;

    public static GrantMemberBadgeDto of(GrantMemberBadgeReq req) {
        return GrantMemberBadgeDto.builder()
                .ploggingCount(req.getPloggingCount())
                .distance(req.getDistance())
                .build();
    }
}
