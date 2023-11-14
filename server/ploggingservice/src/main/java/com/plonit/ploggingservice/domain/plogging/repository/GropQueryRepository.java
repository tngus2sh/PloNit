package com.plonit.ploggingservice.domain.plogging.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;

import static com.plonit.ploggingservice.domain.plogging.QGroup.group;
@Repository
public class GropQueryRepository {

    private final JPAQueryFactory queryFactory;

    public GropQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public HashMap<Long, Long> countCrewPlogging() {
        List<Tuple> results = queryFactory
                .select(group.crewpingKey, group.count())
                .from(group)
                .groupBy(group.crewpingKey)
                .fetch();

        HashMap<Long, Long> countMap = new HashMap<>();
        for (Tuple result : results) {
            countMap.put(result.get(group.crewpingKey), result.get(group.count()));
        }

        return countMap;
    }
}
