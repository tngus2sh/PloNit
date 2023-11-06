package com.plonit.plonitservice.domain.badge;


import com.plonit.plonitservice.domain.crew.Crew;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrewBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_badge_id")
    private Long id;


    @JoinColumn(name = "badge_id")
    @ManyToOne
    private Badge badge;


    @JoinColumn(name = "crew_id")
    @ManyToOne
    private Crew crew;
}
