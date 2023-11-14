package com.plonit.plonitservice.domain.badge.repository;

import com.plonit.plonitservice.api.badge.controller.response.FindBadgeRes;
import com.plonit.plonitservice.common.batch.CrewItemProcessor;
import com.plonit.plonitservice.common.enums.BadgeStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.plonit.plonitservice.domain.badge.QBadge.badge;
import static com.plonit.plonitservice.domain.badge.QBadgeCondition.badgeCondition;
import static com.plonit.plonitservice.domain.badge.QMemberBadge.memberBadge;
import static com.querydsl.core.types.Projections.constructor;

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

    public Optional<FindBadgeRes> findBadge(Long memberKey, List<BadgeStatus> status) {
        return Optional.ofNullable(queryFactory
                .select(constructor(FindBadgeRes.class,
                        badge.code,
                        badge.image,
                        badgeCondition.status,
                        memberBadge.id))
                .from(memberBadge)
                .rightJoin(memberBadge.badge, badge)
                .join(badge.badgeCondition, badgeCondition)
                .where(memberBadge.member.id.eq(memberKey)
                        .and(badgeCondition.status.in(status)))
                .fetchOne());
    }
}
