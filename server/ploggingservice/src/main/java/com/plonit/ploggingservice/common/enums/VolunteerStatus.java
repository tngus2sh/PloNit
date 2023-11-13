package com.plonit.ploggingservice.common.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum VolunteerStatus {

    REQUEST("봉사 요청"),
    APPROVE("봉사 승인");

    private final String description;

}
