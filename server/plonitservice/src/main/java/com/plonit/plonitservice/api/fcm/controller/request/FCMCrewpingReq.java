package com.plonit.plonitservice.api.fcm.controller.request;

import com.plonit.plonitservice.domain.crewping.Crewping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FCMCrewpingReq {
    private Long crewpingId;
    private String crewName;
    private String crewpingName;
    public static FCMCrewpingReq of (Crewping crewping) {
        return FCMCrewpingReq.builder()
                .crewpingId(crewping.getId())
                .crewName(crewping.getCrewName())
                .crewpingName(crewping.getName())
                .build();
    }
}
