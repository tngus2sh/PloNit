package com.plonit.ploggingservice.api.plogging.controller.request;

import com.plonit.ploggingservice.common.enums.Type;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@RequiredArgsConstructor
@Schema(description = "플로깅 시작 request")
public class StartPloggingReq {
    
    @Schema(description = "플로깅 유형 / CREWPING, IND, VOL ")
    @NotNull(message = "유형은 필수 입력값입니다.")
    private Type type;
    
    @Schema(description = "위도")
    @NotNull(message = "위도는 필수 입력값입니다.")
    @Positive
    private Double latitude;
    
    @Schema(description = "경도")
    @NotNull(message = "경도는 필수 입력값입니다.")
    @Positive
    private Double longitude;
    
}
