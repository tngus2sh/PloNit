package com.plonit.ploggingservice.api.plogging.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Schema(description = "플로깅 종료 후 저장 request")
public class EndPloggingReq {

    @Schema(description = "플로깅 id : 플로깅 시작시에 반환된 id")
    @NotNull(message = "플로깅 id는 필수 입력값입니다.")
    @Positive
    private Long ploggingId;

    @Schema(description = "크루핑 id : 크루핑 생성시에 반환된 id")
    @Positive
    private Long crewpingId;
    
    @Schema(description = "거리")
    @NotNull(message = "거리는 필수 입력값입니다.")
    @Positive
    private Double distance;
    
    @Schema(description = "칼로리")
    @NotNull(message = "칼로리는 필수 입력값입니다.")
    @Positive
    private Double calorie;

    @Schema(description = "한줄평")
    private String review;

    @Schema(description = "크루핑시 참여한 사람들 수")
    private Integer people;

    @Schema(description = "좌표")
    private List<Coordinate> coordinates = new ArrayList<>();
    @Data
    @RequiredArgsConstructor
    public static class Coordinate {
        
        @Schema(description = "위도")
        private Double latitude;
        
        @Schema(description = "경도")
        private Double longitude;
    }
    
}
