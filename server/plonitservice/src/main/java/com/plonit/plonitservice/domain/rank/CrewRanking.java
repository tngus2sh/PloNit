package com.plonit.plonitservice.domain.rank;

import com.plonit.plonitservice.domain.crew.Crew;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CrewRanking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_ranking_id")
    private Long id;
    
    @JoinColumn(name = "ranking_period_id")
    @ManyToOne
    private RankingPeriod rankingPeriod;
    
    @JoinColumn(name = "crew_id")
    @ManyToOne
    private Crew crew;
    
    private Integer count;
    
    private Double distance;
    
    private Long time;
    
    private Double calorie;
    
    private Integer totalRanking;
    
    private Integer avgRanking;
    
}
