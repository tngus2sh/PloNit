package com.plonit.ploggingservice.api.excel.controller;

import com.plonit.ploggingservice.api.excel.service.ExcelService;
import com.plonit.ploggingservice.api.excel.service.MailService;
import com.plonit.ploggingservice.api.excel.service.dto.PloggingDto;
import com.plonit.ploggingservice.common.CustomApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Excel API Controller", description = "엑셀 API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/plogging-service/v1/excel")
public class ExcelController {

    private final ExcelService excelService;
    private final MailService mailService;

    @GetMapping()
    public CustomApiResponse<Object> makeExcel() throws IOException {
        log.info("MakeExcel");

        List<PloggingDto> data = new ArrayList<>();
        PloggingDto dto = PloggingDto.builder()
                .id1365("test01")
                .name("김유저")
                .birth("1998-05-12")
                .time(7200l)
                .distance(2.4)
                .startImage("image01")
                .middleImage("image02")
                .endImage("image03")
                .build();
        data.add(dto);

        excelService.makeExcel(data);

        return CustomApiResponse.ok("");
    }

    @GetMapping("/mail")
    public CustomApiResponse<Object> sendEmail() throws MessagingException {
        log.info("SendEmail");

        mailService.sendMail(null);

        return CustomApiResponse.ok("");
    }
}
