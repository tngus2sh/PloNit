package com.plonit.plonitservice.domain.crewping.repository;

import com.plonit.plonitservice.domain.crewping.CrewpingMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static com.plonit.plonitservice.domain.crewping.QCrewpingMember.crewpingMember;
import static com.plonit.plonitservice.domain.member.QMember.member;

@Repository
public class CrewpingMemberQueryRepository {
    private final JPAQueryFactory queryFactory;

    public CrewpingMemberQueryRepository(EntityManager em) { this.queryFactory = new JPAQueryFactory(em); }

    public boolean isCrewpingMember(Long memberId, Long crewpingId) {
        return queryFactory
                .select(crewpingMember)
                .from(crewpingMember)
                .where(crewpingMember.member.id.eq(memberId), crewpingMember.crewping.id.eq(crewpingId))
                .fetchOne() != null;
    }
}