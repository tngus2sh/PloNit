package com.plonit.ploggingservice.api.excel.service.impl;

import com.plonit.ploggingservice.api.excel.service.MailService;
import com.plonit.ploggingservice.api.excel.service.dto.MailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    @Value("${file.path.excel}")
    private String excelFilePath;

    @Value("${mail.username}")
    private String username;

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(MailDto mailDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        String filename = excelFilePath + "\\" + mailDto.getFilename();
        FileSystemResource fsr = new FileSystemResource(filename);

        helper.setFrom(username); // 보내는 사람
        helper.setTo(mailDto.getTo()); // 받는 사람
        helper.setSubject(mailDto.getTitle()); // 제목
        helper.setText(mailDto.getContent()); // 내용
        helper.addAttachment("봉사_플로깅.xlsx", fsr); // 첨부파일

        javaMailSender.send(message);
    }
}
