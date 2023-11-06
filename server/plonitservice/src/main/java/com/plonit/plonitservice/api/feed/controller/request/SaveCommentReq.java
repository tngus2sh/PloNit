package com.plonit.plonitservice.api.feed.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SaveCommentReq {

    @NotNull(message = "피드 Id는 필수 입력값입니다.")
    private int feedId;

    @NotBlank(message = "댓글 내용은 필수 입력값입니다.")
    private String content;

}