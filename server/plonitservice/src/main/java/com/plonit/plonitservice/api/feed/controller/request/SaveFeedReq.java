package com.plonit.plonitservice.api.feed.controller.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class SaveFeedReq {

    @NotBlank(message = "크루 식별키는 필수 입력값입니다.")
    private String crewId;

    @NotBlank(message = "피드 내용은 필수 입력값입니다.")
    private String content;

    private List<MultipartFile> feedPictures;

}