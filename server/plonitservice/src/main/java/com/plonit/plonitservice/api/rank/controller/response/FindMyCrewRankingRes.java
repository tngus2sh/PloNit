package com.plonit.plonitservice.api.rank.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindMyCrewRankingRes {

    private String crewName;

    private Double distance;

    private Integer ranking;

    private Double avgDistance;

    private Integer avgRanking;

    private LocalDateTime startDate;

    private LocalDateTime endDate;


}
