package com.plonit.ploggingservice.api.plogging.controller.request;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class GrantMemberBadgeReq {

    private Long ploggingCount;

    private Double distance;

}
