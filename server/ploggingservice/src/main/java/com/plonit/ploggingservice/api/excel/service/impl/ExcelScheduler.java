package com.plonit.ploggingservice.api.excel.service.impl;

import com.plonit.ploggingservice.api.excel.service.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExcelScheduler {

    private final ExcelService excelService;

    @Scheduled(cron = "0 0 9 * * MON")
    public void makeExcel() {
        log.info("봉사 플로깅 엑셀 파일 생성 및 메일 전송 스케줄러 실행");

        excelService.findVolunteerPloggings();
    }

}
