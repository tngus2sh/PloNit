package com.plonit.ploggingservice.domain.plogging.repository;

import com.plonit.ploggingservice.api.excel.service.dto.PloggingDto;
import com.plonit.ploggingservice.api.excel.service.dto.PloggingPictureDto;
import com.plonit.ploggingservice.api.plogging.controller.response.*;
import com.plonit.ploggingservice.common.enums.Finished;
import com.plonit.ploggingservice.common.enums.Type;
import com.plonit.ploggingservice.common.enums.Type;
import com.plonit.ploggingservice.domain.plogging.Plogging;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.plonit.ploggingservice.domain.plogging.QLatLong.latLong;
import static com.plonit.ploggingservice.domain.plogging.QPlogging.plogging;
import static com.plonit.ploggingservice.domain.plogging.QPloggingPicture.ploggingPicture;
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
                .where(plogging.memberKey.eq(memberKey).and(plogging.date.between(startDate, endDate)))
                .fetch();
    }

    @Transactional(readOnly = true)
    public List<PloggingPeriodRes> findPloggingLogByDayAndType(LocalDate startDate, LocalDate endDate, Long memberKey, Type type) {
        return queryFactory.select(constructor(PloggingPeriodRes.class,
                        plogging.id,
                        plogging.type,
                        plogging.place,
                        plogging.startTime,
                        plogging.endTime,
                        plogging.totalTime,
                        plogging.distance))
                .from(plogging)
                .where(plogging.memberKey.eq(memberKey)
                        .and(plogging.date.between(startDate, endDate))
                        .and(plogging.type.eq(type)))
                .fetch();
    }

    @Transactional(readOnly = true)
    public List<PloggingMonthRes> findPlogginLogByMonth(Long memberKey, LocalDate firstDay, LocalDate lastDay) {
        return queryFactory.select(constructor(PloggingMonthRes.class,
                        plogging.date))
                .from(plogging)
                .where(plogging.date.between(firstDay, lastDay)
                        .and(plogging.memberKey.eq(memberKey)))
                .fetch();
    }

    @Transactional(readOnly = true)
    public Optional<FindPloggingLogRes> findPloggingLogDetail(Long ploggingId, Long memberKey) {
        return Optional.ofNullable(queryFactory.select(constructor(FindPloggingLogRes.class,
                        plogging.id,
                        plogging.type,
                        plogging.place,
                        plogging.startTime,
                        plogging.endTime,
                        plogging.totalTime,
                        plogging.distance,
                        plogging.calorie,
                        plogging.review))
                .from(plogging)
                .where(plogging.id.eq(ploggingId).and(plogging.memberKey.eq(memberKey)))
                .fetchOne());
    }

    @Transactional(readOnly = true)
    public List<UsersRes> findNearByUsers(Long gugunCode) {
        return queryFactory.select(constructor(UsersRes.class,
                        plogging.id,
                        latLong.latitude.min().as("latitude"),
                        latLong.longitude.min().as("longitude")))
                .from(plogging)
                .join(latLong)
                .on(latLong.plogging.eq(plogging))
                .groupBy(plogging.id)
                .where(plogging.gugunCode.eq(gugunCode)
                        .and(plogging.finished.eq(Finished.ACTIVE)))
                .fetch();
    }

    @Transactional(readOnly = true)
    public Integer countPloggingByMemberId(Long memberId) {
        return Math.toIntExact(queryFactory
                .select(plogging.count())
                .from(plogging)
                .where(plogging.memberKey.eq(memberId))
                .fetchOne());
    }

    @Transactional(readOnly = true)
    public List<PloggingDto> findVolunteerPloggings(LocalDateTime startDate, LocalDateTime endDate) {
        List<Plogging> ploggings = queryFactory
                .selectFrom(plogging)
                .join(ploggingPicture)
                .on(ploggingPicture.plogging.eq(plogging))
                .where(plogging.type.eq(Type.VOL), betweenStartTime(startDate, endDate))
                .groupBy(plogging)
                .having(ploggingPicture.count().goe(3))
                .fetch();

        // 각 Plogging ID를 기준으로 PloggingPicture를 조회
        Map<Long, List<PloggingPictureDto>> ploggingPicturesMap = queryFactory
                .selectFrom(ploggingPicture)
                .where(ploggingPicture.plogging.id.in(ploggings.stream().map(Plogging::getId).collect(Collectors.toList())))
                .orderBy(ploggingPicture.createdDate.asc())
                .fetch()
                .stream()
                .collect(Collectors.groupingBy(
                        ploggingPicture -> ploggingPicture.getPlogging().getId(),
                        Collectors.mapping(
                                picture -> PloggingPictureDto.builder().ploggingPicture(picture.getImage()).build(),
                                Collectors.toList()
                        )
                ));

        return ploggings.stream().map(ploggingEntity -> {
            List<PloggingPictureDto> ploggingPictureDtos = ploggingPicturesMap.getOrDefault(ploggingEntity.getId(), Collections.emptyList());
            int length = ploggingPictureDtos.size();

            return PloggingDto.builder()
                    .memberId(ploggingEntity.getMemberKey())
                    .time(ploggingEntity.getTotalTime())
                    .distance(ploggingEntity.getDistance())
                    .startImage(ploggingPictureDtos.get(0).getPloggingPicture())
                    .middleImage(ploggingPictureDtos.get(length / 2).getPloggingPicture())
                    .endImage(ploggingPictureDtos.get(length - 1).getPloggingPicture())
                    .build();
        }).collect(Collectors.toList());
    }

    private BooleanExpression betweenStartTime(LocalDateTime startDate, LocalDateTime endDate) {
        if(startDate == null || endDate == null) {
            return null;
        }
        return plogging.startTime.between(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public FindCountDistanceRes findCountDistance(Long memberKey) {
        return queryFactory.select(constructor(FindCountDistanceRes.class,
                        plogging.count(),
                        plogging.distance.sum()))
                .from(plogging)
                .groupBy(plogging)
                .where(plogging.memberKey.eq(memberKey))
                .fetchOne();
    }

}
