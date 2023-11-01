package com.plonit.plonitservice.domain.crew;

import com.plonit.plonitservice.domain.member.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private Boolean isCrewMaster;

    private Boolean isCrewMember;
    @PrePersist
    public void prePersist() {
        this.isCrewMaster = this.isCrewMaster != null;
        this.isCrewMember = this.isCrewMember != null;
    }
}
