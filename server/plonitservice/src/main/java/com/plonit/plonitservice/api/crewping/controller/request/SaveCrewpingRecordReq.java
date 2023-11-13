package com.plonit.plonitservice.api.crewping.controller.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder(access = PRIVATE)
public class SaveCrewpingRecordReq {

    @NotNull(message = "크루핑 PK는 필수 입력값입니다.")
    private Long crewpingId;

    @NotNull(message = "시작 시간은 필수 입력값입니다.")
    private LocalDateTime startDate;

    @NotNull(message = "종료 시간은 필수 입력값입니다.")
    private LocalDateTime endDate;

    @NotBlank(message = "장소는 필수 입력값입니다.")
    private String place;

    @NotNull(message = "걸린 시간은 필수 입력값입니다.")
    private Long activeTime;

}
