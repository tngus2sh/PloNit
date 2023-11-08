package com.plonit.ploggingservice.api.plogging.service.dto;

import com.plonit.ploggingservice.api.plogging.controller.request.EndPloggingReq;
import lombok.*;

import java.util.Arrays;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class EndPloggingDto {
    
    private Long memberKey;
    
    private Long ploggingId;

    private Long crewpingId;

    private Coordinate[] coordinates;

    private Double distance;

    private Double calorie;
    
    private String review;

    private Integer people;

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
    
    public static EndPloggingDto of(EndPloggingReq request, Long memberKey) {
        EndPloggingDto.Coordinate[] coordinates = Arrays.stream(request.getCoordinates())
                .map(coord -> EndPloggingDto.Coordinate.builder()
                        .latitude(coord.getLatitude())
                        .longitude(coord.getLongitude())
                        .build())
                .toArray(EndPloggingDto.Coordinate[]::new);
        
        return EndPloggingDto.builder()
                .memberKey(memberKey)
                .ploggingId(request.getPloggingId())
                .crewpingId(request.getCrewpingId())
                .coordinates(coordinates)
                .distance(request.getDistance())
                .calorie(request.getCalorie())
                .review(request.getReview())
                .people(request.getPeople())
                .build();
    }
    
}
