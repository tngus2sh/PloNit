package com.plonit.plonitservice.domain.badge;


import com.plonit.plonitservice.common.enums.BadgeCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


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
    @Enumerated(EnumType.STRING)
    private BadgeCode code;

    @Column
    private String name;

    @Column
    private String image;

    @Column
    private Boolean type;

}
