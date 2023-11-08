package com.plonit.plonitservice.api.crew.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class SaveCrewNoticeReq {

    @NotBlank(message = "크루 pk는 필수 입력값입니다.")
    private Long crewId;

    @NotBlank(message = "크루 공지사항 내용은 필수 입력값입니다.")
    @Size(min = 1, max = 300)
    private String content;

}