package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.api.plogging.controller.response.PloggingLogRes;
import com.plonit.ploggingservice.api.plogging.controller.response.PloggingPeriodRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.plonit.ploggingservice.domain.plogging.QLatLong.latLong;
import static com.plonit.ploggingservice.domain.plogging.QPlogging.plogging;
import static com.querydsl.core.types.Projections.constructor;

@Repository
public class PloggingQueryRepository {
    
    private final JPAQueryFactory queryFactory;
    
    public PloggingQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional(readOnly = true)
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
    
    @Transactional(readOnly = true)
    public Optional<PloggingLogRes> findPloggingLogDetail(Long ploggingId, Long memberKey) {
        return Optional.ofNullable(queryFactory.select(constructor(PloggingLogRes.class,
                        plogging.id,
                        plogging.type,
                        plogging.place,
                        plogging.startTime,
                        plogging.endTime,
                        plogging.totalTime,
                        plogging.distance,
                        plogging.calorie))
                .from(plogging)
                .leftJoin(latLong).on(latLong.plogging.id.eq(ploggingId))
                .where(plogging.id.eq(ploggingId).and(plogging.memberKey.eq(memberKey)))
                .fetchOne());
    }
    
}
