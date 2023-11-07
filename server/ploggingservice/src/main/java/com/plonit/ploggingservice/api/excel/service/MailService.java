package com.plonit.ploggingservice.api.excel.service;

import com.plonit.ploggingservice.api.excel.service.dto.MailDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MailService {
    public void sendMail(MailDto mailDto);
}
