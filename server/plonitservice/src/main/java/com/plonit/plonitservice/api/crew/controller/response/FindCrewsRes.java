package com.plonit.plonitservice.api.crew.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class FindCrewsRes {
    private long id;
    private String name;
    private String crewImage;
    private String region;
    private long cntPeople;

    public FindCrewsRes(Long id, String name, String crewImage, String region, Long cntPeople) {
        this.id = id;
        this.name = name;
        this.crewImage = crewImage;
        this.region = region;
        this.cntPeople = cntPeople;
    }
}
