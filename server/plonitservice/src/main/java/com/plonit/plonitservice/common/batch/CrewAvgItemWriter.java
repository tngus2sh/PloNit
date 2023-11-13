package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.api.badge.service.BadgeService;
import com.plonit.plonitservice.api.badge.service.dto.GrantCrewRankDto;
import com.plonit.plonitservice.common.enums.Rank;
import com.plonit.plonitservice.common.util.RedisUtils;
import com.plonit.plonitservice.domain.rank.CrewAvgRanking;
import com.plonit.plonitservice.domain.rank.CrewRanking;
import com.plonit.plonitservice.domain.rank.repository.CrewAvgRankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CrewAvgItemWriter implements ItemWriter<CrewAvgRanking> {

    private final BadgeService badgeService;
    private final CrewAvgRankingRepository crewAvgRankingRepository;
    private final RedisUtils redisUtils;

    @Override
    public void write(List<? extends CrewAvgRanking> items) throws Exception {

        log.info("[ITEMS] = {}", items.toString());

        /* 크루 랭킹 DB에 저장*/
        List<? extends CrewAvgRanking> crewAvgRankings = crewAvgRankingRepository.saveAll(items);

        /* Redis 지우기 */
        redisUtils.deleteRedisKey(Rank.CREW_AVG.getDescription());

        for (int i = 0; i < 3; i++) {
            CrewAvgRanking crewAvgRanking = crewAvgRankings.get(i);
            badgeService.grantCrewRankBadge(GrantCrewRankDto.builder()
                    .crewId(crewAvgRanking.getId())
                    .rank(crewAvgRanking.getRanking())
                    .build());
        }

    }
}
