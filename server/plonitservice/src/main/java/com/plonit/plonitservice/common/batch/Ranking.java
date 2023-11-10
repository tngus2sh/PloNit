package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.domain.rank.RankingPeriod;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Ranking {
    
    private Integer ranking;
    
    private Long memberKey;
    
    private Double distance;

    private RankingPeriod rankingPeriod;
    
}
