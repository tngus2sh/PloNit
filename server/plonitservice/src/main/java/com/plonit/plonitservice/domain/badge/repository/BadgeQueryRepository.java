package com.plonit.plonitservice.domain.badge.repository;


import com.plonit.plonitservice.domain.badge.Badge;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.plonit.plonitservice.domain.badge.QBadge.badge;

@Repository
public class BadgeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BadgeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}
