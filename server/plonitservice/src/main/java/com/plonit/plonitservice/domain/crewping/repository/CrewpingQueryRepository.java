package com.plonit.plonitservice.domain.crewping.repository;

import com.plonit.plonitservice.api.crewping.controller.response.FindCrewpingsRes;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
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
        StringTemplate startDateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", crewping.startDate, ConstantImpl.create("%Y-%m-%d %H:%i"));
        StringTemplate endDateFormat = Expressions.stringTemplate("DATE_FORMAT({0}, {1})", crewping.endDate, ConstantImpl.create("%Y-%m-%d %H:%i"));

        return queryFactory
                .select(Projections.constructor(FindCrewpingsRes.class,
                        crewping.id,
                        crewping.name,
                        crewping.crewpingImage,
                        crewping.place,
                        startDateFormat,
                        endDateFormat,
                        crewping.cntPeople,
                        crewping.maxPeople))
                .from(crewping)
                .where(crewping.crew.id.eq(crewId))
                .fetch();
    }
}
