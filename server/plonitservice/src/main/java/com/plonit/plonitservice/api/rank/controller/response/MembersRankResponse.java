package com.plonit.plonitservice.api.rank.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "회원 랭킹 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembersRankResponse {

    private String rankingPeriod;

    private MembersRank[] membersRanks;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MembersRank {
        private String nickName;

        private String profileImage;

        private Integer ranking;

        private Double distance;

        private Boolean isMine;
    }
}
