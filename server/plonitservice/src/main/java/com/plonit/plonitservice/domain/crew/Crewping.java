package com.plonit.plonitservice.domain.crew;

import com.plonit.plonitservice.domain.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

//@Entity
//@Getter
//@RequiredArgsConstructor
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
//    @CreatedDate
//    @Column(nullable = false, updatable = false)
//    private String start_date;
//}
