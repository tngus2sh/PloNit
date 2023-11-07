package com.plonit.plonitservice.api.rank.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Schema(name = "크루 전체 랭킹 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrewTotalResponse {
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
