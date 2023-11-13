package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.api.badge.service.BadgeService;
import com.plonit.plonitservice.api.badge.service.dto.GrantCrewRankDto;
import com.plonit.plonitservice.common.enums.Rank;
import com.plonit.plonitservice.common.util.RedisUtils;
import com.plonit.plonitservice.domain.rank.CrewRanking;
import com.plonit.plonitservice.domain.rank.repository.CrewRankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CrewItemWriter implements ItemWriter<CrewRanking> {

    private final BadgeService badgeService;
    private final CrewRankingRepository crewRankingRepository;
    private final RedisUtils redisUtils;

    @Override
    public void write(List<? extends CrewRanking> items) throws Exception {
        log.info("[ITEMS] = {}", items.toString());

        /* 크루 랭킹 DB에 저장*/
        List<? extends CrewRanking> crewRankings = crewRankingRepository.saveAll(items);

        /* Redis 지우기 */
        redisUtils.deleteRedisKey(Rank.CREW.getDescription());

        /* 크루 랭킹 */
        for (int i = 0; i < 3; i++) {
            CrewRanking crewRanking = crewRankings.get(i);
            badgeService.grantCrewRankBadge(GrantCrewRankDto.builder()
                    .crewId(crewRanking.getId())
                    .rank(crewRanking.getRanking())
                    .build());
        }
    }
}
