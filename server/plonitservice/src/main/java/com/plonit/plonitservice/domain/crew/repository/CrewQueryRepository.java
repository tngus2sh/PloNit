package com.plonit.plonitservice.domain.crew.repository;

import com.plonit.plonitservice.api.crew.controller.response.FindCrewRes;
import com.plonit.plonitservice.api.crew.controller.response.CrewRankRes;
import com.plonit.plonitservice.api.crew.controller.response.FindCrewsRes;
import com.plonit.plonitservice.api.crew.controller.response.SearchCrewsRes;
import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.CrewMember;
import com.plonit.plonitservice.domain.member.QMember;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.plonit.plonitservice.domain.crew.QCrew.crew;
import static com.plonit.plonitservice.domain.crew.QCrewMember.crewMember;
import static com.querydsl.core.types.ExpressionUtils.count;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.jpa.JPAExpressions.select;

@Repository
public class CrewQueryRepository {

    private final JPAQueryFactory queryFactory;

    public CrewQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<FindCrewsRes> findCrews(){
        return queryFactory
                .select(constructor(FindCrewsRes.class,
                        crew.id,
                        crew.name,
                        crew.crewImage,
                        crew.region,
                        JPAExpressions.select(count(crewMember.id))
                                .from(crewMember)
                                .where(crewMember.isCrewMember.isTrue()
                                        .and(crewMember.crew.id.eq(crew.id))
                )))
                .from(crew)
                .orderBy(crew.createdDate.asc())
                .fetch();
    }
    public Optional<Crew> findCrewWithMember(Long memberId, Long crewId) {
        return Optional.ofNullable(queryFactory
                .select(crewMember.crew)
                .from(crewMember)
                .join(crewMember.crew, crew)
                .where(crewMember.member.id.eq(memberId), crewMember.crew.id.eq(crewId))
                .fetchOne());
    }

    public List<CrewRankRes> findByIds(List<Long> crewIds) {
        return queryFactory.select(constructor(CrewRankRes.class,
                        crew.id,
                        crew.crewImage,
                        crew.name))
                .from(crew)
                .where(crew.id.in(crewIds))
                .fetch();
    }

    public Optional<FindCrewRes> findCrewWithCrewMember(Long crewId, Long memberId) {

        JPQLQuery<Long> countSubQuery = JPAExpressions
                .select(crewMember.count())
                .from(crewMember)
                .where(crewMember.isCrewMember.isTrue()
                        .and(crewMember.crew.id.eq(crewId)));

        JPQLQuery<Boolean> isMyCrewSubQuery = JPAExpressions
                .select(crewMember.id.count().gt(0L))
                .from(crewMember)
                .where(crewMember.isCrewMember.isTrue()
                        .and(crewMember.member.id.eq(memberId))
                        .and(crewMember.crew.id.eq(crewId)));

        JPQLQuery<Boolean> isWaitingSubQuery = JPAExpressions
                .select(crewMember.id.count().gt(0L))
                .from(crewMember)
                .where(crewMember.isCrewMember.isFalse()
                        .and(crewMember.member.id.eq(memberId))
                        .and(crewMember.crew.id.eq(crewId)));

        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(FindCrewRes.class,
                        crew.id,
                        crew.name,
                        countSubQuery,
                        crew.crewImage,
                        crew.region,
                        crew.introduce,
                        crew.notice,
                        crewMember.member.profileImage.as("crewMasterProfileImage"),
                        crewMember.member.nickname.as("crewMasterNickname"),
                        new CaseBuilder()
                                .when(crewMember.isCrewMaster.isTrue()
                                                .and(crewMember.member.id.eq(memberId)))
                                .then(true)
                                .otherwise(false)
                                .as("isCrewMaster"),
                        isMyCrewSubQuery,
                        isWaitingSubQuery))
                .from(crew)
                .join(crewMember).on(crewMember.crew.eq(crew)).fetchJoin()
                .where(crewMember.crew.id.eq(crewId), crewMember.isCrewMaster.isTrue())
                .fetchOne());
    }

    public List<SearchCrewsRes> SearchCrew(int type, String word){
        return queryFactory
                .select(constructor(SearchCrewsRes.class,
                        crew.id,
                        crew.name,
                        crew.cntPeople,
                        crew.crewImage,
                        crew.region
                ))
                .from(crew)
                .where(eqWord(type, word))
                .orderBy(crew.createdDate.asc())
                .fetch();
    }
    private BooleanExpression eqWord(int type, String word) {
        if(type == 1) return crew.name.contains(word);
        else if(type == 2) return crew.region.contains(word);
        else return null;
    }

    public Boolean isValidCrew(Long crewId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(crew)
                .where(crew.id.eq(crewId))
                .fetchFirst();
        return fetchOne != null;
    }
}