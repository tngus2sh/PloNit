package com.plonit.ploggingservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RedisKey {

    /*  랭킹 */
    MEMBER_RANK("멤버 랭킹"),
    CREW_RANK("크루 누적 거리 랭킹"),
    CREW_AVG_RANK("크루 평균 거리 랭킹");

    private final String description;
}
