package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.common.util.RedisUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberItemReader implements ItemReader<Ranking> {

    private final RedisUtils redisUtils;
    private int currentIndex = 0;
    private Long rankingPeriodId = -1L;

    @Override
    public Ranking read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Set<ZSetOperations.TypedTuple<String>> items = redisUtils.getSortedSetRangeWithScores("RANK", currentIndex, currentIndex);

        if (items != null && !items.isEmpty()) {
            ZSetOperations.TypedTuple<String> nextItem = items.iterator().next();
            currentIndex++; // 다음 인덱스로 이동
            log.info("[ITEMREADER VALUE] = {}", nextItem.getValue());
            log.info("[ITEMREADER SCORE] = {}", nextItem.getScore());
            log.info("[ITEMREADER RANK] = {}", currentIndex);
            
            /* 랭킹 기간 가져오기 */
            if (rankingPeriodId < 0) {
                
            }
            
            return Ranking.builder()
                    .ranking(currentIndex)
                    .memberKey(Long.valueOf(nextItem.getValue()))
                    .distance(nextItem.getScore())
                    .build();
        } else {
            // 더 이상 데이터가 없는 경우 currentIndex, rankingPeriodId 초기화
            currentIndex = 0;
            rankingPeriodId = -1L;
            return null;
        }
    }
}
