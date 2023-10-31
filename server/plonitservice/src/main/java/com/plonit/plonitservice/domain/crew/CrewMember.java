package com.plonit.plonitservice.domain.crew;

import com.plonit.plonitservice.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

//@Entity
//@Getter
//@RequiredArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class CrewMember {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "crew_member_id")
//    private Long id;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "crew_id")
//    private Crew crew;
//
//    @ColumnDefault("false")
//    private Boolean isCrewMaster;
//}
