package com.plonit.plonitservice.api.rank.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(name = "회원 랭킹 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembersRankResponse {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    private List<MembersRank> membersRanks = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MembersRank { 
        private String nickName;

        private String profileImage;

        private Integer ranking;

        private Double distance;
 
        private Boolean isMine;
    }
}
