package com.plonit.plonitservice.domain.crew.repository;

import com.plonit.plonitservice.api.crew.controller.response.FindCrewRes;
import com.plonit.plonitservice.api.crew.controller.response.CrewRankRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewsRes;
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
import static com.querydsl.core.types.Projections.constructor;

@Repository
public class CrewQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CrewQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<FindCrewsRes> findCrews(){
        return queryFactory
                .select(constructor(FindCrewsRes.class,
                        crew.id,
                        crew.name,
                        crew.cntPeople,
                        crew.crewImage,
                        crew.region
                ))
                .from(crew)
                .orderBy(crew.createdDate.asc())
                .fetch();
    }
    public Optional<Crew> findCrewWithMember(Long memberId, Long crewId) {
        return Optional.ofNullable(queryFactory
                .select(crewMember.crew)
                .from(crewMember)
                .join(crewMember.crew, crew)
                .where(crewMember.member.id.eq(memberId), crewMember.crew.id.eq(crewId))
                .fetchOne());
    }

    public List<CrewRankRes> findByIds(List<Long> crewIds) {
        return queryFactory.select(constructor(CrewRankRes.class,
                        crew.id,
                        crew.crewImage,
                        crew.name))
                .from(crew)
                .where(crew.id.in(crewIds))
                .fetch();
    }

    public Optional<FindCrewRes> findCrewWithCrewMember(Long crewId) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(FindCrewRes.class,
                        crew.id,
                        crew.name,
                        crew.cntPeople,
                        crew.crewImage,
                        crew.region,
                        crew.introduce,
                        crew.notice,
                        crewMember.member.profileImage.as("crewMasterProfileImage"),
                        crewMember.member.nickname.as("crewMasterNickname")))
                .from(crew)
                .join(crewMember).on(crewMember.crew.eq(crew)).fetchJoin()
                .where(crewMember.crew.id.eq(crewId), crewMember.isCrewMaster.isTrue())
                .fetchOne());
    }
}