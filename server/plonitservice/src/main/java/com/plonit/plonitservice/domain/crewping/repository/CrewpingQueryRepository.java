package com.plonit.plonitservice.domain.crewping.repository;

import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingsRes;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.plonit.plonitservice.domain.crewping.QCrewping.crewping;

@Repository
public class CrewpingQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CrewpingQueryRepository(EntityManager em) { this.queryFactory = new JPAQueryFactory(em); }

    public List<FindCrewpingsRes> findCrewpings(Long crewId) {
        return queryFactory
                .select(Projections.constructor(FindCrewpingsRes.class,
                        crewping.id,
                        crewping.name,
                        crewping.crewpingImage,
                        crewping.place,
                        crewping.startDate,
                        crewping.endDate,
                        crewping.cntPeople,
                        crewping.maxPeople))
                .from(crewping)
                .where(crewping.crew.id.eq(crewId))
                .fetch();
    }
}
