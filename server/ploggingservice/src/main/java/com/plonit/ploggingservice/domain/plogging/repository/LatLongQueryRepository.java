package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.api.plogging.controller.response.FindLatLongRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.plonit.ploggingservice.domain.plogging.QLatLong.latLong;
import static com.querydsl.core.types.Projections.constructor;

@Repository
public class LatLongQueryRepository {

    private final JPAQueryFactory queryFactory;

    public LatLongQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional(readOnly = true)
    public List<FindLatLongRes> findLatLongByPloggingId(Long ploggingId) {
        return queryFactory.select(constructor(FindLatLongRes.class, 
                        latLong.latitude,
                        latLong.longitude))
                .from(latLong)
                .where(latLong.plogging.id.eq(ploggingId))
                .fetch();
    } 
}
