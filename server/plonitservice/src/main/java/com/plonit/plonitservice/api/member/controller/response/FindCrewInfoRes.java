package com.plonit.plonitservice.api.member.controller.response;

import com.plonit.plonitservice.api.crew.controller.response.FindCrewsRes;
import lombok.Data;

@Data
public class FindCrewInfoRes{
    private long id;
    private String name;
    private String crewImage;
    private String region;
    private long cntPeople;
    private long floggingCount;

    public FindCrewInfoRes (Long id, String name, String crewImage, String region, Long cntPeople) {
        this.id = id;
        this.name = name;
        this.crewImage = crewImage;
        this.region = region;
        this.cntPeople = cntPeople;
    }

}
