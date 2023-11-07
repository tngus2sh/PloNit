package com.plonit.plonitservice.domain.crew.repository;

import com.plonit.plonitservice.api.crew.controller.response.FindCrewMasterMemberRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewMemberRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewRes;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.CrewMember;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.plonit.plonitservice.domain.crew.QCrew.crew;
import static com.plonit.plonitservice.domain.crew.QCrewMember.crewMember;
import static com.plonit.plonitservice.domain.member.QMember.member;

@Repository
public class CrewMemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CrewMemberQueryRepository(EntityManager em) { this.queryFactory = new JPAQueryFactory(em); }

    @Transactional(readOnly = true)
    public Integer isValidCrewMember(Long memberId, Long crewId) {
        return Math.toIntExact(queryFactory
                .select(crewMember.count())
                .from(crewMember)
                .where(crewMember.member.id.eq(memberId), crewMember.crew.id.eq(crewId))
                .fetchFirst());
    }

    @Transactional(readOnly = true)
    public Boolean isValidCrewMaster(Long memberId, Long crewId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(crewMember)
                .where(crewMember.crew.id.eq(crewId)
                        .and(crewMember.isCrewMaster.isTrue())
                        .and(crewMember.member.id.eq(memberId)))
                .fetchFirst();
        return fetchOne != null;
    }

    @Transactional(readOnly = true)
    public List<CrewMember> findByCrewId(Long crewId) {
        return queryFactory
                .selectFrom(crewMember)
                .join(crewMember.crew, crew)
                .join(crewMember.member, member).fetchJoin()
                .where(crewMember.crew.id.eq(crewId))
                .fetch();
    }

}
