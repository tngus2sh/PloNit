package com.plonit.plonitservice.common.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BadgeCode {

    /* 플로깅 횟수 */
    COUNT_1("플로깅 1회 달성"),
    COUNT_10("플로깅 10회 달성"),
    COUNT_50("플로깅 50회 달성"),
    COUNT_100("플로깅 100회 달성"),

    /* 플로깅 거리 */
    DISTANCE_1("플로깅 거리 1km 달성"),
    DISTANCE_10("플로깅 거리 10km 달성"),
    DISTANCE_20("플로깅 거리 20km 달성"),
    DISTANCE_30("플로깅 거리 30km 달성"),
    DISTANCE_50("플로깅 거리 50km 달성"),
    DISTANCE_100("플로깅 거리 100km 달성"),

    /* 랭킹 */
    RANK_1("랭킹 1위"),
    RANK_2("랭킹 2위"),
    RANK_3("랭킹 3위");

    private final String description;

}
