package com.plonit.plonitservice.api.crew.controller.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SaveCrewReq {

    @NotBlank(message = "크루명은 필수 입력값입니다.")
    @Size(min = 2, max = 50)
    private String name;

    private MultipartFile crewImage;

    @NotBlank(message = "크루 소개는 필수 입력값입니다.")
    private String introduce;

    @NotBlank(message = "주요 활동 지역은 필수 입력값입니다.")
    private String gugunCode;

}