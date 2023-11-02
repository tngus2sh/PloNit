package com.plonit.plonitservice.api.feed.controller.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
@Data
public class SaveFeedReq {

    @NotBlank(message = "피드 내용은 필수 입력값입니다.")
    private String content;

    private MultipartFile crewImage;

}