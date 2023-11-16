package com.plonit.plonitservice.api.rank.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindMyRankingRes {

    private Double distance;

    private Integer ranking;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean isSeason;

    public FindMyRankingRes(Double distance, Integer ranking, LocalDateTime startDate, LocalDateTime endDate) {
        this.distance = distance;
        this.ranking = ranking;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isSeason = false;
    }
}
