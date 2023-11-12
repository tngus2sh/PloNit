package com.plonit.plonitservice.domain.badge.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.plonit.plonitservice.domain.badge.QMemberBadge.memberBadge;
import static com.plonit.plonitservice.domain.crew.QCrewMember.crewMember;

@Repository
public class MemberBadgeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberBadgeQueryRepository(EntityManager em) { this.queryFactory = new JPAQueryFactory(em); }

    public Integer countByMemberBadgeByMemberId(Long memberId) {
        return Math.toIntExact(queryFactory
                .select(memberBadge.count())
                .from(memberBadge)
                .where(memberBadge.member.id.eq(memberId))
                .fetchOne());
    }
}
