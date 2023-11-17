package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.api.badge.service.BadgeService;
import com.plonit.plonitservice.api.badge.service.dto.GrantMemberRankDto;
import com.plonit.plonitservice.common.enums.Rank;
import com.plonit.plonitservice.common.util.RedisUtils;
import com.plonit.plonitservice.domain.rank.MemberRanking;
import com.plonit.plonitservice.domain.rank.repository.MemberRankingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberItemWriter implements ItemWriter<MemberRanking> {

    private final BadgeService badgeService;
    private final MemberRankingRepository memberRankingRepository;
    private final RedisUtils redisUtils;

    @Override
    public void write(List<? extends MemberRanking> items) throws Exception {
        log.info("[ITEMS] = {}", items.toString());
        /* 멤버 랭킹 DB에 저장 */
        List<? extends MemberRanking> memberRankings = memberRankingRepository.saveAll(items);

        /* Redis 지우기 */
        redisUtils.deleteRedisKey(Rank.MEMBER.getDescription());

        for (int i = 0; i < 3; i++) {
            MemberRanking memberRanking = memberRankings.get(i);
            badgeService.grantRankBadge(GrantMemberRankDto.builder()
                    .memberKey(memberRanking.getMember().getId())
                    .rank(memberRanking.getRanking())
                    .build());
        }
    }
}

