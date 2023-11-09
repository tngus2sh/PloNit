package com.plonit.plonitservice.api.rank.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class RankingBatch {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private RedisItemReader redisItemReader;
    @Autowired
    private RedisItemProcessor redisItemProcessor;
    @Autowired
    private RedisItemWriter redisItemWriter;

    @Bean
    public Job rankingJob() {
        return jobBuilderFactory.get("rankingJob")
                .start(rankingStep())
                .build();
    }

    @Bean
    public Step rankingStep() {
        return stepBuilderFactory.get("rankingStep")
                .<String, Ranking>chunk(10)
                .reader(redisItemReader)
                .processor(redisItemProcessor)
                .writer(redisItemWriter)
                .build();
    }

}
