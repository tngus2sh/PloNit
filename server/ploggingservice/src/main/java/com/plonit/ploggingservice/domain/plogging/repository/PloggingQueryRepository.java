package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.api.plogging.controller.response.PloggingLogRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingPeriodRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static com.plonit.ploggingservice.domain.plogging.QPlogging.plogging;
import static com.querydsl.core.types.Projections.constructor;

@Repository
public class PloggingQueryRepository {
    
    private final JPAQueryFactory queryFactory;
    
    public PloggingQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<PloggingPeriodRes> findPloggingLogByDay(LocalDate startDate, LocalDate endDate, Long memberKey) {
        return queryFactory.select(constructor(PloggingPeriodRes.class,
                        plogging.id,
                        plogging.type,
                        plogging.place,
                        plogging.startTime,
                        plogging.endTime,
                        plogging.totalTime,
                        plogging.distance))
                .from(plogging)
                .where(plogging.id.eq(memberKey).and(plogging.date.between(startDate, endDate)))
                .fetch();
    }
    
}
