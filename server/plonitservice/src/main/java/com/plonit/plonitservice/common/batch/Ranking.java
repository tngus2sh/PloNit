package com.plonit.plonitservice.common.batch;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Ranking {
    
    private Integer ranking;
    
    private Long memberKey;
    
    private Double distance;
    
}
