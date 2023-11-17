package com.plonit.plonitservice.domain.crew;

import com.plonit.plonitservice.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@DynamicUpdate
@Table(name = "crew_member")
public class CrewMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crew_member_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "members_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "crew_id")
    private Crew crew;

    @ColumnDefault("false")
    private Boolean isCrewMaster;

    @ColumnDefault("false")
    private Boolean isCrewMember;

    public static CrewMember toEntity (Crew crew, Member member) {
        return CrewMember.builder()
                .member(member)
                .isCrewMaster(true)
                .isCrewMember(true)
                .crew(crew)
                .build();
    }

    public static CrewMember memberToEntity (Crew crew, Member member) {
        return CrewMember.builder()
                .member(member)
                .crew(crew)
                .build();
    }

    public void changeIsCrewMember() {
        this.isCrewMember = true;
    }
}
