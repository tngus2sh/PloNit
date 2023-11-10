package com.plonit.plonitservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BadgeStatus {

    DISTANCE("거리"),

    COUNT("횟수"),

    RANKING("랭킹");

    private final String text;
}
