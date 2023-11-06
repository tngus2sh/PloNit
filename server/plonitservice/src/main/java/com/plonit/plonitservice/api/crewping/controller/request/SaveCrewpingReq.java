package com.plonit.plonitservice.api.crewping.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class SaveCrewpingReq {

    @NotNull(message = "크루 PK는 필수 입력값입니다.")
    private Long crewId;

    @NotBlank(message = "크루핑명은 필수 입력값입니다.")
    @Size(min = 5, max = 50)
    private String name;

    @NotNull(message = "크루핑 이미지는 필수 입력값입니다.")
    private MultipartFile crewpingImage;

    @NotNull(message = "시작 시간은 필수 입력값입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime startDate;

    @NotNull(message = "종료 시간은 필수 입력값입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private LocalDateTime endDate;

    @NotNull(message = "최대 인원은 필수 입력값입니다.")
    private Integer maxPeople;

    @NotBlank(message = "장소는 필수 입력값입니다.")
    private String place;

    @NotBlank(message = "활동 소개는 필수 입력값입니다.")
    private String introduce;

    private String notice;

}
