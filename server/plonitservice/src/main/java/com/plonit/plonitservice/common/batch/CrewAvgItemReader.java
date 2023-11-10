package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.common.enums.Rank;
import com.plonit.plonitservice.common.util.RedisUtils;
import com.plonit.plonitservice.domain.rank.RankingPeriod;
import com.plonit.plonitservice.domain.rank.repository.RankingPeriodQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class CrewAvgItemReader implements ItemReader<Ranking> {

    private final RankingPeriodQueryRepository rankingPeriodQueryRepository;
    private final RedisUtils redisUtils;
    private int currentIndex = 0;
    private RankingPeriod rankingPeriod = null;

    @Override
    public Ranking read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Set<ZSetOperations.TypedTuple<String>> items = redisUtils.getSortedSetRangeWithScores(Rank.CREW_AVG.getDescription(), currentIndex, currentIndex);

        if (items != null && !items.isEmpty()) {
            ZSetOperations.TypedTuple<String> nextItem = items.iterator().next();
            currentIndex++; // 다음 인덱스로 이동
            log.info("[ITEMREADER VALUE] = {}", nextItem.getValue());
            log.info("[ITEMREADER SCORE] = {}", nextItem.getScore());
            log.info("[ITEMREADER RANK] = {}", currentIndex);

            /* 랭킹 기간 가져오기 */
            if (rankingPeriod == null) {
                Optional<RankingPeriod> rankingPeriodOptional = rankingPeriodQueryRepository.findRecentId();
                rankingPeriodOptional.ifPresent(period -> rankingPeriod = period);
            }

            return Ranking.builder()
                    .ranking(currentIndex)
                    .id(Long.valueOf(nextItem.getValue()))
                    .distance(nextItem.getScore())
                    .rankingPeriod(rankingPeriod)
                    .build();
        } else {
            // 더 이상 데이터가 없는 경우 currentIndex, rankingPeriodId 초기화
            currentIndex = 0;
            rankingPeriod = null;
            return null;
        }
    }
}
