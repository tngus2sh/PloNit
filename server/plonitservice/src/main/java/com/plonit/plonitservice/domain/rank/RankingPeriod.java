package com.plonit.plonitservice.domain.rank;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class RankingPeriod {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ranking_period_id")
    private Long id;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
}
