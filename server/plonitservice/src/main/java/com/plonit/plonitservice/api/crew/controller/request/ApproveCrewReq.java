package com.plonit.plonitservice.api.crew.controller.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class ApproveCrewReq {

    @NotBlank(message = "크루 식별키는 필수 입력값입니다.")
    private long crewId;

    @NotBlank(message = "크루원 식별키는 필수 입력값입니다.")
    private long memberId;

    @NotBlank(message = "승인 상태는 필수 입력값입니다.")
    private String status;
}