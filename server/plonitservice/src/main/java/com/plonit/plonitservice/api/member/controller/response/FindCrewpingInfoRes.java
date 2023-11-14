package com.plonit.plonitservice.api.member.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
public class FindCrewpingInfoRes {

    private Long id;
    private String crewName;
    private String crewpingName;
    private String crewpingImage;
    private Long dDay;
    private String startDate;
    private String endDate;
    private String place;
    private int cntPeople;
    private List<String> memberProfileImage;
    private Boolean isMaster;

}
