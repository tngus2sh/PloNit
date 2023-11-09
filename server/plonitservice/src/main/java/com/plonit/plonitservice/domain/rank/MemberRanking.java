package com.plonit.plonitservice.domain.rank;


import com.plonit.plonitservice.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberRanking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_ranking_id")
    private Long id;
    
    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;
    
    @JoinColumn(name = "ranking_period_id")
    @ManyToOne
    private RankingPeriod rankingPeriod;
    
    private Integer count;
    
    private Double distance;
    
    private Long time;
    
    private Double calorie;
    
    private Integer rank;
    
}
