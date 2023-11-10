package com.plonit.plonitservice.domain.rank.repository;

import com.plonit.plonitservice.domain.rank.RankingPeriod;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.plonit.plonitservice.domain.rank.QRankingPeriod.rankingPeriod;

@Repository
public class RankingPeriodQueryRepository {

    private final JPAQueryFactory queryFactory;

    public RankingPeriodQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional(readOnly = true)
    public Optional<RankingPeriod> findRecentId() {
        return Optional.ofNullable(queryFactory
                .selectFrom(rankingPeriod)
                .orderBy(rankingPeriod.endDate.desc())
                .limit(1)
                .fetchOne());
    }
}
