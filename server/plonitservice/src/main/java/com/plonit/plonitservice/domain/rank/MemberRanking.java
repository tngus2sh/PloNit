package com.plonit.plonitservice.domain.rank;


import com.plonit.plonitservice.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MemberRanking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_ranking_id")
    private Long id;
    
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    
    @JoinColumn(name = "ranking_period_id")
    @ManyToOne(fetch =FetchType.LAZY)
    private RankingPeriod rankingPeriod;
    
    private Double distance;
    
    private Integer ranking;
    
}
