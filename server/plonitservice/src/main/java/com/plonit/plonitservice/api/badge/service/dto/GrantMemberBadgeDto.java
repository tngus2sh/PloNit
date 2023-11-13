package com.plonit.plonitservice.api.badge.service.dto;

import com.plonit.plonitservice.api.badge.controller.request.GrantMemberBadgeReq;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GrantMemberBadgeDto {

    private Long memberKey;
    private Integer ploggingCount;

    private Double distance;

    public static GrantMemberBadgeDto of(GrantMemberBadgeReq req, Long memberKey) {
        return GrantMemberBadgeDto.builder()
                .memberKey(memberKey)
                .ploggingCount(req.getPloggingCount())
                .distance(req.getDistance())
                .build();
    }

}
