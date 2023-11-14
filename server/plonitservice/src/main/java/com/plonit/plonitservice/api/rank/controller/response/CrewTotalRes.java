package com.plonit.plonitservice.api.rank.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Schema(name = "크루 전체 랭킹 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewTotalRes {
    private String rankingPeriod;
    private List<CrewsRanks> crewsRanks = new LinkedList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CrewsRanks {
        private String nickName;

        private String profileImage;

        private Integer ranking;

        private Double distance;

        private Boolean isMine;
    }
    
}
