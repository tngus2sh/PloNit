package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.api.plogging.controller.response.PloggingHelpRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.plonit.ploggingservice.domain.plogging.QPloggingHelp.ploggingHelp;
import static com.querydsl.core.types.Projections.constructor;

@Repository
public class PloggingHelpQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PloggingHelpQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<PloggingHelpRes> findPloggingHelp(LocalDate today, Long gugunCode) {
        return queryFactory.select(constructor(PloggingHelpRes.class,
                        ploggingHelp.latitude,
                        ploggingHelp.longitude,
                        ploggingHelp.place,
                        ploggingHelp.image,
                        ploggingHelp.context))
                .from(ploggingHelp)
                .where(ploggingHelp.gugunCode.eq(gugunCode).and(ploggingHelp.createdDate.between(today.atStartOfDay(), today.plusDays(1).atStartOfDay())))
                .fetch();
    }
    
}
