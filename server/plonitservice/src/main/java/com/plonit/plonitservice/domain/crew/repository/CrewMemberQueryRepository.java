package com.plonit.plonitservice.domain.crew.repository;

import com.plonit.plonitservice.api.crew.controller.response.FindCrewMasterMemberRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewMemberRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewRes;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.CrewMember;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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

    public Integer isValidCrewMember(Long memberId, Long crewId) {
        return Math.toIntExact(queryFactory
                .select(crewMember.count())
                .from(crewMember)
                .where(crewMember.member.id.eq(memberId), crewMember.crew.id.eq(crewId))
                .fetchFirst());
    }

    // 크루원 유효 체크 (크루장인지도 확인 가능)
    public Boolean isValidCrewMember(Long memberId, Long crewId, Boolean crewMaster) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(crewMember)
                .where(crewMember.crew.id.eq(crewId)
                        .and(crewMember.member.id.eq(memberId))
                        .and(eqMaster(crewMaster)))
                .fetchFirst();
        return fetchOne != null;
    }
    private BooleanExpression eqMaster(Boolean crewMaster) {
        if(!crewMaster) return null; // null 반환시 자동으로 조건절에서 제거 됨
        return crewMember.isCrewMaster.isTrue();
    }

    // 크루 조회 (크루 멤버, 크루, 멤버)
    public List<CrewMember> findByCrewId(Long crewId) {
        return queryFactory
                .selectFrom(crewMember)
                .join(crewMember.crew, crew)
                .join(crewMember.member, member).fetchJoin()
                .where(crewMember.crew.id.eq(crewId))
                .fetch();
    }

    // 승인 대기 중인 크루원 조회
    public List<CrewMember> findByWaitingCrewId(Long crewId) {
        return queryFactory
                .selectFrom(crewMember)
                .join(crewMember.crew, crew)
                .join(crewMember.member, member).fetchJoin()
                .where(crewMember.crew.id.eq(crewId)
                        .and(crewMember.isCrewMember.isFalse()))
                .fetch();
    }

    // 크루 멤버 조회
    public Optional<CrewMember> findByMemberIdAndCrewId(Long memberId, Long crewId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(crewMember)
                .where(crewMember.crew.id.eq(crewId)
                        .and(crewMember.member.id.eq(memberId)))
                .fetchOne());
    }
}
