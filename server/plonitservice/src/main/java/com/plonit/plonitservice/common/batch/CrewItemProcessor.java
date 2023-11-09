package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.domain.rank.CrewRanking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CrewItemProcessor implements ItemProcessor<String, CrewRanking> {
    @Override
    public CrewRanking process(String item) throws Exception {
        return null;
    }
}
