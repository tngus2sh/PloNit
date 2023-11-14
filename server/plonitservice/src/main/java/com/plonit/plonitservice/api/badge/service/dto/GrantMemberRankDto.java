package com.plonit.plonitservice.api.badge.service.dto;

import com.plonit.plonitservice.api.badge.controller.request.GrantMemberRankReq;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GrantMemberRankDto {

    private Long memberKey;

    private Integer rank;

    public static GrantMemberRankDto of(GrantMemberRankReq req, Long memberKey) {
        return GrantMemberRankDto.builder()
                .memberKey(memberKey)
                .rank(req.getRank())
                .build();
    }

}
