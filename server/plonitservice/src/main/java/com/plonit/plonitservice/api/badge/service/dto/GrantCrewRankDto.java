package com.plonit.plonitservice.api.badge.service.dto;

import com.plonit.plonitservice.api.badge.controller.request.GrantCrewRankReq;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GrantCrewRankDto {

    private Long crewId;

    private Integer rank;

    public static GrantCrewRankDto of(GrantCrewRankReq req) {
        return GrantCrewRankDto.builder()
                .crewId(req.getCrewId())
                .rank(req.getRank())
                .build();
    }

}
