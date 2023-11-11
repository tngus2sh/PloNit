package com.plonit.plonitservice.api.fcm.controller.request;

import lombok.Data;

@Data
public class FCMReq {
    private Long targetMemberId;
    private String title;
    private String body;
}
