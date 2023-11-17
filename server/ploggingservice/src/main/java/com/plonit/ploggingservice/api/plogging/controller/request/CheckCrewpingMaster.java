package com.plonit.ploggingservice.api.plogging.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckCrewpingMaster {

    private Long memberKey;

    private Long crewpingId;

}
