package com.plonit.plonitservice.api.badge.controller.request;

import lombok.Data;

@Data
public class GrantCrewRankReq {

    private Long crewId;

    private Integer rank;

}
