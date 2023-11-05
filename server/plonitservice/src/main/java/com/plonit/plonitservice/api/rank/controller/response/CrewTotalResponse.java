package com.plonit.plonitservice.api.rank.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "크루 전체 랭킹 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewTotalResponse {
    private String rankingPeriod;
    private CrewsRanks[] crewsRanks;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrewsRanks {
        private String nickName;

        private String profileImage;

        private Integer ranking;

        private Double distance;

        private Boolean isMine;
    }
    
}
