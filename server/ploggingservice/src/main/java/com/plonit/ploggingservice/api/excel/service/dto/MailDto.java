package com.plonit.ploggingservice.api.excel.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@Builder(access = PRIVATE)
public class MailDto {

    private String to;
    private String subject;
    private String content;
    private AttachFileDto attachFileDto;
}
