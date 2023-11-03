package com.plonit.plonitservice.api.member.controller.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Data
public class UpdateMemberReq {

    @NotEmpty(message = "이름은 필수 입력값입니다.")
    @Size(min = 2, max = 20)
    private String name;

    @NotEmpty(message = "닉네임은 필수 입력값입니다.")
    @Size(min = 2, max = 20)
    private String nickname;

    private MultipartFile profileImage;

    @NotEmpty(message = "성별은 필수 입력값입니다.")
    private String gender;

    @NotEmpty(message = "생년월일은 필수 입력값입니다.")
    private String birth;

    @NotEmpty(message = "활동지역은 필수 입력값입니다.")
    private String region;

    @Size(min = 1, max = 3)
    private String height;

    @Size(min = 1, max = 3)
    private String weight;

    private String id1365;

}