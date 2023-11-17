package com.plonit.plonitservice.api.badge.controller.request;

import lombok.Data;

@Data
public class MembersBadgeReq {

    private Long memberId;

    private Long badgeId;

}
