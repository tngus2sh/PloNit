package com.plonit.plonitservice.domain.crew.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@Repository
public class CrewQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CrewQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

}