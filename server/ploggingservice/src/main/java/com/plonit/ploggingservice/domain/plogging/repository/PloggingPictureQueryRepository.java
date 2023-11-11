package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.api.plogging.controller.response.FindPloggingImagesRes;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.plonit.ploggingservice.domain.plogging.QPloggingPicture.ploggingPicture;
import static com.querydsl.core.types.Projections.constructor;

@Repository
public class PloggingPictureQueryRepository {

    private final JPAQueryFactory queryFactory;

    public PloggingPictureQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional(readOnly = true)
    public List<FindPloggingImagesRes> findImagesByPloggingId(Long ploggingId) {
        return queryFactory.select(constructor(FindPloggingImagesRes.class,
                        ploggingPicture.image))
                .from(ploggingPicture)
                .where(ploggingPicture.plogging.id.eq(ploggingId))
                .fetch();
    }
}
