package com.plonit.ploggingservice.api.plogging.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@RequiredArgsConstructor
@Schema(description = "플로깅 이미지 request")
public class HelpPloggingReq {

    @Schema(description = "위도")
    @NotBlank(message = "위도는 필수 입력값입니다.")
    @Positive
    private Double latitude;

    @Schema(description = "경도")
    @NotBlank(message = "경도는 필수 입력값입니다.")
    @Positive
    private Double longitude;

    @Schema(description = "이미지")
    private MultipartFile image;
    
    @Schema(description = "내용")
    private String context;
    
}
