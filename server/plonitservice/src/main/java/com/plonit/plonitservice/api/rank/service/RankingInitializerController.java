//package com.plonit.plonitservice.api.rank.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.quartz.*;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.quartz.JobBuilder.newJob;
//
//@Slf4j
//@Controller
//public class RankingInitializerController {
//    
//    @Autowired
//    private Scheduler scheduler;
//    
//    @PostConstruct
//    public void start() {
//        // 매달 1일과 15일에 실행되도록 cron 표현식 설정
//        String cronExpression = "0 0 1,15 * * ?";
//        JobDetail aggreReqJobDetail = buildJobDetail(RankingInitializerJob.class, "rankingInitializerJob", "rankingInitializerGroup", new HashMap());
//        try {
//            scheduler.scheduleJob(aggreReqJobDetail, buildJobTrigger(cronExpression));
//        } catch (SchedulerException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Trigger buildJobTrigger(String scheduleExp) {
//        return TriggerBuilder.newTrigger()
//                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
//    }
//
//    public JobDetail buildJobDetail(Class job, String name, String group, Map params) {
//        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.putAll(params);
//
//        return newJob(job).withIdentity(name, group)
//                .usingJobData(jobDataMap)
//                .build();
//    }
//}
