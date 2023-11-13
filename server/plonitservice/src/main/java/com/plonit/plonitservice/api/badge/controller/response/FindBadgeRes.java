package com.plonit.plonitservice.api.badge.controller.response;

import com.plonit.plonitservice.common.enums.BadgeCode;
import com.plonit.plonitservice.common.enums.BadgeStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindBadgeRes {

    private BadgeCode badgeCode;

    private String image;

    private BadgeStatus status;

    private Boolean check; // true : 획득, false : 획득 X

}
