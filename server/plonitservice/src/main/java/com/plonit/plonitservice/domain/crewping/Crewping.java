package com.plonit.plonitservice.domain.crewping;

import com.plonit.plonitservice.common.enums.Status;
import com.plonit.plonitservice.domain.TimeBaseEntity;
import com.plonit.plonitservice.domain.crew.Crew;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = PROTECTED)
public class Crewping extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crewping_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "crew_id")
    private Crew crew;

    private String crewName;

    private String name;

    @Column(length = 500)
    private String crewpingImage;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private int cntPeople;

    private int maxPeople;

    @Column(length = 500)
    private String place;

    @Column(length = 500)
    private String introduce;

    @Column(name = "active_time")
    private long activeTime;

    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private Status status;


    public void updateCurrentPeople(boolean flag) {
        if(flag) {
            this.cntPeople++;
        }
        else {
            this.cntPeople--;
        }
    }
}
