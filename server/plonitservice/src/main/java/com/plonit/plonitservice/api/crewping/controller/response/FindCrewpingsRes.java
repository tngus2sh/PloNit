package com.plonit.plonitservice.api.crewping.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder(access = PRIVATE)
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
