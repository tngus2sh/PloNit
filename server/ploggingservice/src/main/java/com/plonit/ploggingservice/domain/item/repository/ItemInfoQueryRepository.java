package com.plonit.ploggingservice.domain.item.repository;

import com.plonit.ploggingservice.api.item.controller.response.FindItemRes;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.plonit.ploggingservice.domain.item.QItemInfo.itemInfo;

@Repository
public class ItemInfoQueryRepository {

    private final JPAQueryFactory queryFactory;

    public ItemInfoQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<FindItemRes> findTrashcan(Long gugunCode, boolean flag) {
        return queryFactory
                .select(Projections.constructor(FindItemRes.class,
                        itemInfo.latitude,
                        itemInfo.longitude))
                .from(itemInfo)
                .where(itemInfo.contentType.eq(flag), itemInfo.gugunCode.eq(gugunCode))
                .fetch();
    }

}
