package com.plonit.plonitservice.domain.rank;

import com.plonit.plonitservice.domain.crew.Crew;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
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
    
    private Double distance;
    
    private Integer ranking;
    
}
