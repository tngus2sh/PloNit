package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import com.plonit.plonitservice.domain.rank.MemberRanking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class MemberItemProcessor implements ItemProcessor<Ranking, MemberRanking> {
    
    private final MemberRepository memberRepository;

    @Override
    public MemberRanking process(Ranking item) throws Exception {
        log.info("[ITEM -> BATCH] = {}", item);

        /* memberKey로 member 불러오기 */
        Member member = null;
        Optional<Member> memberId = memberRepository.findById(item.getId());
        if (memberId.isPresent()) {
            member = memberId.get();
        }

        return MemberRanking.builder()
                .member(member)
                .rankingPeriod(item.getRankingPeriod())
                .distance(item.getDistance())
                .ranking(item.getRanking())
                .build();
    }
}

