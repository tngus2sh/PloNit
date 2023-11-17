package com.plonit.ploggingservice.api.excel.service;

import com.plonit.ploggingservice.api.excel.service.dto.MailDto;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Transactional
public interface MailService {
    public void sendMail(MailDto mailDto) throws MessagingException;
}
