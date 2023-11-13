package com.plonit.plonitservice.api.badge.controller.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class GrantMemberBadgeReq {

    private Integer ploggingCount;

    private Double distance;

}
