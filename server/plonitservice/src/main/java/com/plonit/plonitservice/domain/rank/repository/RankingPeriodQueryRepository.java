package com.plonit.plonitservice.domain.rank.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class RankingPeriodQueryRepository {

    private final JPAQueryFactory queryFactory;

    public RankingPeriodQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    Optional<Long> findRecentId() {
//        return queryFactory.select()
//                .from(rankingPeriod)
    }
}
