package com.plonit.plonitservice.domain.badge.repository;


import com.plonit.plonitservice.api.badge.controller.response.FindBadgeRes;
import com.plonit.plonitservice.common.enums.BadgeStatus;
import com.plonit.plonitservice.domain.badge.Badge;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.plonit.plonitservice.domain.badge.QBadge.badge;
import static com.plonit.plonitservice.domain.badge.QBadgeCondition.badgeCondition;
import static com.plonit.plonitservice.domain.badge.QMemberBadge.memberBadge;
import static com.querydsl.core.types.Projections.constructor;

@Repository
@Transactional(readOnly = true)
public class BadgeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BadgeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<FindBadgeRes> findBadge(List<Long> badgeIds, List<BadgeStatus> status, Boolean type) {
        return queryFactory
                .select(constructor(FindBadgeRes.class,
                        badge.code,
                        badge.image,
                        badgeCondition.status,
                        badge.id,
                        new CaseBuilder()
                                .when(badge.id.in(badgeIds))
                                .then(true)
                                .otherwise(false)))
                .from(badge)
                .join(badge.badgeCondition, badgeCondition)
                .where(badgeCondition.status.in(status),
                        badge.type.eq(type))
                .fetch();
    }

}
