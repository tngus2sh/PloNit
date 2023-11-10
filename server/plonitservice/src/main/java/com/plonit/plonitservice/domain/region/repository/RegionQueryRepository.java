package com.plonit.plonitservice.domain.region.repository;

import com.plonit.plonitservice.api.region.controller.response.DongRes;
import com.plonit.plonitservice.api.region.controller.response.GugunRes;
import com.plonit.plonitservice.api.region.controller.response.SidoGugunCodeRes;
import com.plonit.plonitservice.api.region.controller.response.SidoRes;
import com.plonit.plonitservice.domain.region.Dong;
import com.plonit.plonitservice.domain.region.Gugun;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.plonit.plonitservice.domain.region.QDong.dong;
import static com.plonit.plonitservice.domain.region.QGugun.gugun;
import static com.plonit.plonitservice.domain.region.QSido.sido;
import static com.querydsl.core.types.Projections.constructor;

@Repository
public class RegionQueryRepository {

    private final JPAQueryFactory queryFactory;

    public RegionQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional(readOnly = true)
    public List<SidoRes> findSido() {
        return queryFactory.select(constructor(SidoRes.class,
                        sido.code,
                        sido.name))
                .from(sido)
                .fetch();
    }
    
    @Transactional(readOnly = true)
    public List<GugunRes> findGugun(Long sidoCode) {
        return queryFactory.select(constructor(GugunRes.class,
                        gugun.code,
                        gugun.name))
                .from(gugun)
                .where(gugun.sido.code.eq(sidoCode))
                .fetch();
    }

    @Transactional(readOnly = true)
    public List<DongRes> findDong(Long gugunCode) {
        return queryFactory.select(constructor(DongRes.class,
                        dong.code,
                        dong.name))
                .from(dong)
                .where(dong.gugun.code.eq(gugunCode))
                .fetch();
    }

    /* 시도 이름, 구군 이름으로 시도 코드와 구군 코드 찾기 */
    public Optional<SidoGugunCodeRes> findSidoGugunCode(String sidoName, String gugunName) {
        return Optional.ofNullable(queryFactory.select(constructor(SidoGugunCodeRes.class,
                        gugun.sido.code,
                        gugun.code))
                .from(gugun)
                .where(gugun.name.like(gugunName).and(gugun.sido.name.contains(sidoName)))
                .fetchOne());
    }

    public Optional<Dong> findDongFetchJoin(Long dongCode) {
        return Optional.ofNullable(queryFactory
                .selectFrom(dong)
                .leftJoin(dong.gugun, gugun).fetchJoin()
                .leftJoin(gugun.sido, sido).fetchJoin()
                .where(dong.code.eq(dongCode))
                .fetchOne());
    }

    public Optional<Gugun> findGugunFetchJoin(Long gugunCode) {
        return Optional.ofNullable(queryFactory
                .selectFrom(gugun)
                .leftJoin(gugun.sido, sido).fetchJoin()
                .where(gugun.code.eq(gugunCode))
                .fetchOne());
    }
}
