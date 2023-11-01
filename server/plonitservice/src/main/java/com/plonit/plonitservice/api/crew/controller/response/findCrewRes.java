package com.plonit.plonitservice.api.crew.controller.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class findCrewRes {
    private long id;
    private String name;
    private String crewImage;
    private String region;

}
