package com.plonit.plonitservice.api.badge.service.dto;

import com.plonit.plonitservice.api.badge.controller.request.MembersBadgeReq;
import com.plonit.plonitservice.domain.badge.Badge;
import com.plonit.plonitservice.domain.badge.MemberBadge;
import com.plonit.plonitservice.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembersBadgeDto {

    private Long memberId;

    private Long badgeId;

    public static MembersBadgeDto of(MembersBadgeReq membersBadgeReq) {
        return MembersBadgeDto.builder()
                .memberId(membersBadgeReq.getMemberId())
                .badgeId(membersBadgeReq.getBadgeId())
                .build();
    }

    public static MemberBadge toEntity(Badge badge, Member member) {
        return MemberBadge.builder()
                .badge(badge)
                .member(member)
                .build();
    }
}
