package com.plonit.plonitservice.domain.crew.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.plonit.plonitservice.domain.crew.QCrewMember.crewMember;

@Repository
public class CrewMemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CrewMemberQueryRepository(EntityManager em) { this.queryFactory = new JPAQueryFactory(em); }

    public Integer isValidCrewMember(Long memberId, Long crewId) {
        return Math.toIntExact(queryFactory
                .select(crewMember.count())
                .from(crewMember)
                .where(crewMember.member.id.eq(memberId), crewMember.crew.id.eq(crewId))
                .fetchFirst());
    }
}
