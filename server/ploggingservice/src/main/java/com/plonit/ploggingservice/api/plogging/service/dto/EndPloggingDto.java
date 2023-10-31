package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.api.plogging.controller.request.EndPloggingRequest;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Data
@RequiredArgsConstructor
public class EndPloggingDto {
    
    private Long memberKey;
    
    private Long ploggingId;

    private Coordinate[] coordinates;

    private Double distance;

    private Double calorie;
    
    private String review;

    @Builder
    public EndPloggingDto(Long memberKey, Long ploggingId, Coordinate[] coordinates, Double distance, Double calorie, String review) {
        this.memberKey = memberKey;
        this.ploggingId = ploggingId;
        this.coordinates = coordinates;
        this.distance = distance;
        this.calorie = calorie;
        this.review = review;
    }

    @Data
    @RequiredArgsConstructor
    public static class Coordinate {
        private Double latitude;
        private Double longitude;

        @Builder
        public Coordinate(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
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
