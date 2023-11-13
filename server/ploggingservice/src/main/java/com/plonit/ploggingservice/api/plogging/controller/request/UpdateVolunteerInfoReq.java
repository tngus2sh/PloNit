package com.plonit.ploggingservice.api.plogging.controller.request;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateVolunteerInfoReq {

    private Long memberKey;
    private String name;

    private String phoneNumber;

    private String id1365;

    private String email;

    private String birth;

}
