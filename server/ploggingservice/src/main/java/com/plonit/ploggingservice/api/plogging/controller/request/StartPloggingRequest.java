package com.plonit.ploggingservice.api.plogging.controller.request;

import com.plonit.ploggingservice.common.enums.Type;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
public class StartPloggingRequest {
    
    @NotBlank(message = "유형은 필수 입력값입니다.")
    private Type type;
    
    @NotBlank(message = "위도는 필수 입력값입니다.")
    @Positive
    private Double latitude;
    
    @NotBlank(message = "경도는 필수 입력값입니다.")
    @Positive
    private Double longitude;
    
    private Long crewpingId;

    @Builder
    public StartPloggingRequest(Type type, Double latitude, Double longitude, Long crewpingId) {
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.crewpingId = crewpingId;
    }
}
