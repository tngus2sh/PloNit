package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.common.enums.Type;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StartPloggingDto {

    private Type type;

    private Double latitude;

    private Double longitude;

    private Long crewpingId;
    
    private String memberKey;
    
}
