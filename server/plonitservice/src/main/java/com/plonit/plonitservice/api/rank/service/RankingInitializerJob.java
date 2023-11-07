//package com.plonit.plonitservice.api.rank.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.quartz.InterruptableJob;
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.quartz.UnableToInterruptJobException;
//import org.springframework.batch.core.JobParameters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class RankingInitializerJob extends QuartzJobBean implements InterruptableJob {
//    
//    @Autowired RankService rankService;
//    
//    @Override
//    public void interrupt() throws UnableToInterruptJobException {
//        
//    }
//
//    @Override
//    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//        log.info("RankingInitializerJob 실행");
//        
//        // Redis에서 랭킹 정보 가져와서 DB에 저장
//        // Spring Batch Job을 실행
//        try {
//            jobLauncher.run(dataMigrationJob, new JobParameters());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
