package com.plonit.plonitservice.api.crewping.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder(access = PRIVATE)
public class UpdateCrewpingStatusReq {

    @NotNull(message = "크루핑 PK는 필수 입력값입니다.")
    private Long crewpingId;

}
