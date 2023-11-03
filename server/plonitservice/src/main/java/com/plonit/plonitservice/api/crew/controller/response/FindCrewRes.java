package com.plonit.plonitservice.api.crew.controller.response;

import com.plonit.plonitservice.domain.crew.Crew;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class FindCrewRes {
    private long id;
    private String name;
    private int cntPeople;
    private String crewImage;
    private String region;
    private String introduce;
    private String rankingInfo;
    private int totalRanking;
    private int avgRanking;

    public static FindCrewRes of(Crew crew) {
        return FindCrewRes.builder()
                .id(crew.getId())
                .name(crew.getName())
                .cntPeople(crew.getCntPeople())
                .crewImage(crew.getCrewImage())
                .region(crew.getRegion())
                .introduce(crew.getIntroduce())
                .build();
    }
}
