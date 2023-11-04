package com.plonit.plonitservice.domain.crewping;

import com.plonit.plonitservice.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@AllArgsConstructor
@Builder(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
@Table(name = "crewping_member")
public class CrewpingMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crewping_member_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "crewping_id")
    private Crewping crewping;

    @ColumnDefault("false")
    private Boolean isCrewpingMaster;


    public static CrewpingMember of(Member member, Crewping crewping, Boolean isCrewpingMaster) {
        return CrewpingMember.builder()
                .member(member)
                .crewping(crewping)
                .isCrewpingMaster(isCrewpingMaster)
                .build();
    }
}
