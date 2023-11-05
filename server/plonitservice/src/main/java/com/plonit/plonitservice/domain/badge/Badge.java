package com.plonit.plonitservice.domain.badge;


import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Badge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id;

    @JoinColumn(name = "badge_condition_id")
    @OneToOne
    private BadgeCondition badgeCondition;

    @Column
    private String name;

    @Column
    private String image;

    @Column
    private Boolean type;

}
