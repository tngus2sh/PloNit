package com.plonit.plonitservice.domain.badge;


import com.plonit.plonitservice.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberBadge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_badge_id")
    private Long id;

    @JoinColumn(name = "badge_id")
    @ManyToOne
    private Badge badge;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

}
