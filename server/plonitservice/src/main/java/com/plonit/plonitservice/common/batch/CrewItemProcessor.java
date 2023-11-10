package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.domain.crew.Crew;
import com.plonit.plonitservice.domain.crew.repository.CrewRepository;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.rank.CrewRanking;
import com.plonit.plonitservice.domain.rank.MemberRanking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CrewItemProcessor implements ItemProcessor<Ranking, CrewRanking> {

    private final CrewRepository crewRepository;

    @Override
    public CrewRanking process(Ranking item) throws Exception {
        log.info("[ITEM -> BATCH] = {}", item);

        /* id로 crew 불러오기 */
        Crew crew = null;
        Optional<Crew> crewId = crewRepository.findById(item.getId());
        if (crewId.isPresent()) {
            crew = crewId.get();
        }

        return CrewRanking.builder()
                .crew(crew)
                .rankingPeriod(item.getRankingPeriod())
                .distance(item.getDistance())
                .ranking(item.getRanking())
                .build();
    }
}
