package com.plonit.plonitservice.domain.badge.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class BadgeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public BadgeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


}
