package com.plonit.plonitservice.domain.badge;


import com.plonit.plonitservice.common.enums.BadgeStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BadgeCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_condition_id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private BadgeStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int value;

}
