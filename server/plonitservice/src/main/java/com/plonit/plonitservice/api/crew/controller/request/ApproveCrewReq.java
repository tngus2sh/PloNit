package com.plonit.plonitservice.api.crew.controller.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ApproveCrewReq {

    @NotNull(message = "크루 식별키는 필수 입력값입니다.")
    private Long crewId;

    @NotNull(message = "크루원 식별키는 필수 입력값입니다.")
    private Long memberId;

    @NotNull(message = "승인 상태는 필수 입력값입니다.")
    private Boolean status;
}