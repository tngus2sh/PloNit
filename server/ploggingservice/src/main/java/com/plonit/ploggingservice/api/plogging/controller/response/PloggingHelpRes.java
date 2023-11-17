package com.plonit.ploggingservice.api.plogging.controller.response;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PloggingHelpRes {
    
    private Double latitude;
    
    private Double longitude;
    
    private String place;
    
    private String image;
    
    private String context;

    private Long ploggingHelpId;
}
