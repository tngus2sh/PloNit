package com.plonit.plonitservice.domain.rank.repository;

import com.plonit.plonitservice.api.rank.controller.response.FindMyCrewRankingRes;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.plonit.plonitservice.domain.crew.QCrew.crew;
import static com.plonit.plonitservice.domain.rank.QCrewAvgRanking.crewAvgRanking;
import static com.plonit.plonitservice.domain.rank.QCrewRanking.crewRanking;
import static com.plonit.plonitservice.domain.rank.QRankingPeriod.rankingPeriod;
import static com.querydsl.core.types.Projections.constructor;

@Repository
public class CrewRankingQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CrewRankingQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<FindMyCrewRankingRes> findMyCrewRanking(List<Long> crewIds) {
        return queryFactory
                .select(constructor(FindMyCrewRankingRes.class,
                        crew.name,
                        crewRanking.distance,
                        crewRanking.ranking,
                        JPAExpressions
                        .select(crewAvgRanking.distance)
                        .from(crewAvgRanking)
                        .where(crewAvgRanking.crew.id.eq(crewRanking.crew.id)
                                .and(crewAvgRanking.rankingPeriod.endDate.eq(rankingPeriod.endDate))),
                        JPAExpressions
                                .select(crewAvgRanking.ranking)
                                .from(crewAvgRanking)
                                .where(crewAvgRanking.crew.id.eq(crewRanking.crew.id)
                                        .and(crewAvgRanking.rankingPeriod.endDate.eq(rankingPeriod.endDate))),
                        rankingPeriod.startDate,
                        rankingPeriod.endDate))
                .from(crewRanking)
                .join(crewRanking.crew, crew)
                .join(crewRanking.rankingPeriod, rankingPeriod)
                .where(crewRanking.crew.id.in(crewIds))
                .orderBy(rankingPeriod.endDate.desc())
                .fetch();
    }

}
