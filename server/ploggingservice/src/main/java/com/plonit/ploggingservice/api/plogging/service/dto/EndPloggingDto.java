package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.api.plogging.controller.request.EndPloggingRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Data
@Builder 
@RequiredArgsConstructor
public class EndPloggingDto {
    
    private Long memberKey;
    
    private Long ploggingId;

    private Coordinate[] coordinates;

    private Double distance;

    private Double calorie;
    
    private String review;

    @Data
    @Builder
    @RequiredArgsConstructor
    public static class Coordinate {
        private Double latitude;
        private Double longitude;
    }
    
    public static EndPloggingDto of(EndPloggingRequest request, Long memberKey) {
        EndPloggingDto.Coordinate[] coordinates = Arrays.stream(request.getCoordinates())
                .map(coord -> EndPloggingDto.Coordinate.builder()
                        .latitude(coord.getLatitude())
                        .longitude(coord.getLongitude())
                        .build())
                .toArray(EndPloggingDto.Coordinate[]::new);
        
        return EndPloggingDto.builder()
                .memberKey(memberKey)
                .ploggingId(request.getPloggingId())
                .coordinates(coordinates)
                .distance(request.getDistance())
                .calorie(request.getCalorie())
                .review(request.getReview())
                .build();
    }
    
}
