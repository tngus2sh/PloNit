package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.api.plogging.controller.request.StartPloggingReq;
import com.plonit.ploggingservice.common.enums.Finished;
import com.plonit.ploggingservice.common.enums.Type;
import com.plonit.ploggingservice.domain.plogging.Plogging;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@RequiredArgsConstructor
public class StartPloggingDto {
    
    private Long memberKey;

    private Type type;

    private Double latitude;

    private Double longitude;

    private Long crewpingId;

    @Builder
    public StartPloggingDto(Long memberKey, Type type, Double latitude, Double longitude, Long crewpingId) {
        this.memberKey = memberKey;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.crewpingId = crewpingId;
    }

    public static StartPloggingDto of (StartPloggingReq request, Long memberKey) {
        return StartPloggingDto.builder()
                .memberKey(memberKey)
                .type(request.getType())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .crewpingId(request.getCrewpingId())
                .build();
    }
    
    public static Plogging toEntity(Long memberKey, Type type, String place, LocalDateTime startTime, Finished finished, LocalDate date) {
        return Plogging.builder()
                .memberKey(memberKey)
                .type(type)
                .place(place)
                .startTime(startTime)
                .finished(finished)
                .date(date)
                .build();
    }
}
