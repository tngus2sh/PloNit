package com.plonit.plonitservice.domain.feed.repository;

import com.plonit.plonitservice.domain.feed.Like;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.plonit.plonitservice.domain.feed.QLike.like;

@Repository
public class LikeQueryRepository {

    private final JPAQueryFactory queryFactory;

    public LikeQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Like findLikeByMemberAndFeed(Long memberId, Long feedId) {
        return queryFactory
                .select(like)
                .from(like)
                .where(like.feed.id.eq(feedId), like.member.id.eq(memberId))
                .fetchOne();
    }

}
