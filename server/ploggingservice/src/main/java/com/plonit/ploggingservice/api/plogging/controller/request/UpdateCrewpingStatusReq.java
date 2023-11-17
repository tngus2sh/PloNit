package com.plonit.ploggingservice.api.plogging.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCrewpingStatusReq {

    // 크루핑 id
    private Long crewpingId;

}
