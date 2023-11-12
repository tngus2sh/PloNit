package com.plonit.plonitservice.domain.crewping.repository;

import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingMembersByMasterRes;
import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingMembersRes;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

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

    public List<FindCrewpingMembersByMasterRes> findCrewpingMembersByMaster(Long crewpingId) {
        return queryFactory
                .select(Projections.constructor(FindCrewpingMembersByMasterRes.class,
                        crewpingMember.member.id,
                        crewpingMember.member.profileImage,
                        crewpingMember.member.nickname
                        ))
                .from(crewpingMember)
                .join(crewpingMember).on(crewpingMember.member.eq(member)).fetchJoin()
                .where(crewpingMember.crewping.id.eq(crewpingId))
                .fetch();
    }

    public List<FindCrewpingMembersRes> findCrewpingMembers(Long crewpingId) {
        return queryFactory
                .select(Projections.constructor(FindCrewpingMembersRes.class,
                        crewpingMember.member.profileImage,
                        crewpingMember.member.nickname
                ))
                .from(crewpingMember)
                .join(crewpingMember).on(crewpingMember.member.eq(member)).fetchJoin()
                .where(crewpingMember.crewping.id.eq(crewpingId))
                .fetch();
    }
}