package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.api.plogging.controller.request.StartPloggingRequest;
import com.plonit.ploggingservice.common.enums.Type;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StartPloggingDto {
    
    private Long memberKey;

    private Type type;

    private Double latitude;

    private Double longitude;

    private Long crewpingId;
    
    public static StartPloggingDto of (StartPloggingRequest request, Long memberKey) {
        return StartPloggingDto.builder()
                .memberKey(memberKey)
                .type(request.getType())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .crewpingId(request.getCrewpingId())
                .build();
    }
}
