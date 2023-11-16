package com.plonit.plonitservice.domain.rank.repository;

import com.plonit.plonitservice.api.rank.controller.response.FindMyRankingRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.plonit.plonitservice.domain.member.QMember.member;
import static com.plonit.plonitservice.domain.rank.QMemberRanking.memberRanking;
import static com.plonit.plonitservice.domain.rank.QRankingPeriod.rankingPeriod;
import static com.querydsl.core.types.Projections.constructor;

@Repository
public class MemberRankingQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberRankingQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<FindMyRankingRes> findMyRanking(Long memberKey) {
        return queryFactory
                .select(constructor(FindMyRankingRes.class,
                        memberRanking.distance,
                        memberRanking.ranking,
                        rankingPeriod.startDate,
                        rankingPeriod.endDate))
                .from(memberRanking)
                .join(memberRanking.rankingPeriod, rankingPeriod)
                .join(memberRanking.member, member)
                .where(memberRanking.member.id.eq(memberKey))
                .orderBy(rankingPeriod.endDate.asc())
                .fetch();
    }
}
