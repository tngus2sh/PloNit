package com.plonit.plonitservice.domain.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.plonit.plonitservice.domain.member.QMember.member;

@Repository
public class MemberQueryRepository {

    private final JPAQueryFactory queryFactory;

    public MemberQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Transactional(readOnly = true)
    public Boolean existKakaoId(long kakoId) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(member)
                .where(member.kakaoId.eq(kakoId))
                .fetchFirst();
        return fetchOne != null;
    }

    @Transactional(readOnly = true)
    public Boolean existEmail(String email) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(member)
                .where(member.email.eq(email))
                .fetchFirst();
        return fetchOne != null;
    }

    @Transactional(readOnly = true)
    public Boolean existNickname(String nickname) {
        Integer fetchOne = queryFactory
                .selectOne()
                .from(member)
                .where(member.nickname.eq(nickname))
                .fetchFirst();
        return fetchOne != null;
    }

}