package com.plonit.ploggingservice.api.plogging.controller.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class VolunteerPloggingReq {

    private Long ploggingId;
    private String name;

    private String phoneNumber;

    private String id1365;

    private String email;

    private String birth;

}
