package com.plonit.plonitservice.api.auth.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDTO {
    private String access_token;
    private String refresh_token;

}
