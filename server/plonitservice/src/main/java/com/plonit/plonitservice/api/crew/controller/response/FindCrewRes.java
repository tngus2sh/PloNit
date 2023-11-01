package com.plonit.plonitservice.api.crew.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindCrewRes {
    private long id;
    private String name;
    private String crewImage;
    private String region;
    private String introduce;
    private int cntPeople;
    private String rankingInfo;
    private int totalRanking;
    private int avgRanking;
}
