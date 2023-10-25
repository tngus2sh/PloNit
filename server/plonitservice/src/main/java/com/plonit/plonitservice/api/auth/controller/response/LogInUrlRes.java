package com.plonit.plonitservice.api.auth.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class LogInUrlRes {
    private String url;
}