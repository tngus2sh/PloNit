//package com.plonit.plonitservice.common.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@Slf4j
//public class BatchConfig {
//    
//    private final JobBuilderFactory jobBuilderFactory;
//    private final StepBuilderFactory stepBuilderFactory;
//
//    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
//        this.jobBuilderFactory = jobBuilderFactory;
//        this.stepBuilderFactory = stepBuilderFactory;
//    }
//
//    @Bean
//    public Job dataMigrationJob(MongoItemReader<YourDataClass> itemReader, RepositoryItemWriter<YourDataClass> itemWriter) {
//        Step step = stepBuilderFactory.get("dataMigrationStep")
//                .<YourDataClass, YourDataClass>chunk(100) // 한 번에 처리할 데이터 양을 설정
//                .reader(itemReader)
//                .writer(itemWriter)
//                .build();
//
//        return jobBuilderFactory.get("dataMigrationJob")
//                .incrementer(new RunIdIncrementer())
//                .start(step)
//                .build();
//    }
//}
