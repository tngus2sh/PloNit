package com.plonit.plonitservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rank {

    MEMBER("MEMBER_RANK"),
    CREW("CREW_RANK"),
    CREW_AVG("CREW_AVG_RANK");

    private final String description;

}
