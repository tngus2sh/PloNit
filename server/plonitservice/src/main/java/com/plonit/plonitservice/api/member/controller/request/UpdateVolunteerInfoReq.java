package com.plonit.plonitservice.api.member.controller.request;

import lombok.Builder;
import lombok.Data;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder(access = PRIVATE)
public class UpdateVolunteerInfoReq {

    private Long memberKey;
    private String name;

    private String phoneNumber;

    private String id1365;

    private String email;

    private String birth;
}
