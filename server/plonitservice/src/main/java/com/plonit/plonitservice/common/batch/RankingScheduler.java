package com.plonit.plonitservice.common.batch;

import com.plonit.plonitservice.domain.rank.RankingPeriod;
import com.plonit.plonitservice.domain.rank.repository.RankingPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class RankingScheduler {
    private final JobLauncher jobLauncher;
    private final Job job;
    private final RankingPeriodRepository rankingPeriodRepository;

    //    @Scheduled(cron = "0 0 1,15 * * ?") // 매월 1일과 15일에 실행
    @Scheduled(cron = "0 0/1 * * * ?")
    public void runBatchJob() throws Exception {
        /* 랭킹 기간 생성 */
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        int dayOfMonth = now.getDayOfMonth();

        RankingPeriod rankingPeriod = null;
        if (dayOfMonth == 1) {
            rankingPeriod = RankingPeriod.builder()
                    .startDate(now)
                    .endDate(now.plusDays(13))
                    .build();
        } else if (dayOfMonth == 15) {
            YearMonth yearMonth = YearMonth.from(now);
            LocalDateTime lastDayofMonthWithTime = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX);
            
            rankingPeriod = RankingPeriod.builder()
                    .startDate(now)
                    .endDate(lastDayofMonthWithTime)
                    .build();
        }
        rankingPeriodRepository.save(rankingPeriod);
        
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job, jobParameters);
    }
    
}
