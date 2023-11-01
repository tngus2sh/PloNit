package com.plonit.plonitservice.domain.crew;

import com.plonit.plonitservice.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

//@Entity
//@Getter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "crewping_member")
//public class CrewpingMember {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "crewping_member_id")
//    private Long id;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "members_id")
//    private Member member;
//
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "crewping_id")
//    private Crewping crewping;
//
//    @ColumnDefault("false")
//    private Boolean isCrewpingMaster;
//}
