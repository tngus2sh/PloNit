package com.plonit.ploggingservice.api.plogging.controller.request;

import lombok.Data;

@Data
public class UpdatePloggingHelpStatusReq {

    private Long ploggingHelpId;

    private Boolean isActive;

}
