package com.plonit.plonitservice.api.crew.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FindCrewsRes {
    private long id;
    private String name;
    private Integer cntPeople;
    private String crewImage;
    private String region;
}
