package com.plonit.plonitservice.api.fcm.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FCMReq {
    private Long targetMemberId;
    private String title;
    private String body;
}
