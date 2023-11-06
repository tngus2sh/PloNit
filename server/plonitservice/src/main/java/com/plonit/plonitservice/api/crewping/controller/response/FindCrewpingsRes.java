package com.plonit.plonitservice.api.crewping.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindCrewpingsRes {

    private Long crewpingId;
    private String name;
    private String crewpingImage;
    private String place;
    private String startDate;
    private String endDate;
    private int cntPeople;
    private int maxPeople;
}
