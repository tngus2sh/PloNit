package com.plonit.plonitservice.domain.crew.repository;

import com.plonit.plonitservice.api.crew.controller.response.FindCrewsRes;
import com.plonit.plonitservice.domain.crew.Crew;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.plonit.plonitservice.domain.crew.QCrew.crew;
import static com.plonit.plonitservice.domain.crew.QCrewMember.crewMember;

@Repository
public class CrewQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CrewQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<FindCrewsRes> findCrews(){
        return queryFactory
                .select(Projections.constructor(FindCrewsRes.class,
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
    public Optional<Crew> findCrew(Long memberId, Long crewId) {
        return Optional.ofNullable(queryFactory
                .select(crewMember.crew)
                .from(crewMember)
                .join(crewMember.crew, crew)
                .where(crewMember.member.id.eq(memberId), crewMember.crew.id.eq(crewId))
                .fetchOne());
    }
}