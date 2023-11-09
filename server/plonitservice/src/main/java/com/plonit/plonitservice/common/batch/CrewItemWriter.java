package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.domain.rank.CrewRanking;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CrewItemWriter implements ItemWriter<CrewRanking> {
    @Override
    public void write(List<? extends CrewRanking> items) throws Exception {
        
    }
}
