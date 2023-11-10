package com.plonit.plonitservice.common.config;

import com.plonit.plonitservice.common.batch.*;
import com.plonit.plonitservice.domain.rank.CrewAvgRanking;
import com.plonit.plonitservice.domain.rank.CrewRanking;
import com.plonit.plonitservice.domain.rank.MemberRanking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    
    /* 회원 랭킹 */
    private final MemberItemReader memberItemReader;
    private final MemberItemProcessor memberItemProcessor;
    private final MemberItemWriter memberItemWriter;
    
    /* 크루 누적 랭킹 */
    private final CrewItemReader crewItemReader;
    private final CrewItemProcessor crewItemProcessor;
    private final CrewItemWriter crewItemWriter;
    
    /* 크루 평균 랭킹 */
    private final CrewAvgItemReader crewAvgItemReader;
    private final CrewAvgItemProcessor crewAvgItemProcessor;
    private final CrewAvgItemWriter crewAvgItemWriter;
    
    
    
    private int currentIndex = 0;

    @Bean("job")
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(memberStep())
                .next(crewStep())
                .build();
    }

    @JobScope
    @Bean("memberStep")
    public Step memberStep() {
        log.info("[STEP] member");
        return stepBuilderFactory.get("memberStep")
                .<Ranking, MemberRanking>chunk(100)
                .reader(memberItemReader)
                .processor(memberItemProcessor)
                .writer(memberItemWriter)
                .build();
    }

    @JobScope
    @Bean("crewStep")
    public Step crewStep() {
        log.info("[STEP] crewStep");
        return stepBuilderFactory.get("crewStep")
                .<Ranking, CrewRanking>chunk(100)
                .reader(crewItemReader)
                .processor(crewItemProcessor)
                .writer(crewItemWriter)
                .build();
    }
    
    @JobScope
    @Bean("crewAvgStep")
    public Step crewAvgStep() {
        log.info("[STEP] crewAvgStep");
        return stepBuilderFactory.get("crewAvgStep")
                .<Ranking, CrewAvgRanking>chunk(100)
                .reader(crewAvgItemReader)
                .processor(crewAvgItemProcessor)
                .writer(crewAvgItemWriter)
                .build();
    }
    

}
