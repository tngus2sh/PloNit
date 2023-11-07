package com.plonit.ploggingservice.api.excel.service.impl;

import com.plonit.ploggingservice.api.excel.service.MailService;
import com.plonit.ploggingservice.api.excel.service.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    @Override
    public void sendMail(MailDto mailDto) {

    }
}
