package com.plonit.plonitservice.domain.crew;

import com.plonit.plonitservice.common.enums.Status;
import com.plonit.plonitservice.domain.TimeBaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

//@Entity
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Crewping extends TimeBaseEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "crewping_id")
//    private Long id;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "crew_id")
//    private Crew crew;
//
//    private String crewName;
//
//    private String name;
//
//    @Column(length = 500)
//    private String crewpingImage;
//
//    private String startDate;
//
//    private String endDate;
//
//    private int cntPeople;
//
//    private int maxPeople;
//
//    @Column(length = 500)
//    private String place;
//
//    @Column(length = 500)
//    private String introduce;
//
//    @Column(length = 500)
//    private String notice;
//
//    private long active_time;
//
//    @ColumnDefault("'ACTIVE'")
//    @Enumerated(EnumType.STRING)
//    private Status status;
//
//}
