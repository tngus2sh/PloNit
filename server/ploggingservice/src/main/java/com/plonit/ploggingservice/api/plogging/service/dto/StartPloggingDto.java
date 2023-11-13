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

    @Builder
    public StartPloggingDto(Long memberKey, Type type, Double latitude, Double longitude) {
        this.memberKey = memberKey;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static StartPloggingDto of (StartPloggingReq request, Long memberKey) {
        return StartPloggingDto.builder()
                .memberKey(memberKey)
                .type(request.getType())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();
    }
    
    public static Plogging toEntity(Long memberKey, Type type, String place, Long gugunCode, LocalDateTime startTime, Finished finished, LocalDate date) {
        return Plogging.builder()
                .memberKey(memberKey)
                .type(type)
                .place(place)
                .gugunCode(gugunCode)
                .startTime(startTime)
                .finished(finished)
                .date(date)
                .build();
    }
}
