package com.plonit.plonitservice.api.region.controller.response;

import lombok.*;

@Data
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DongRes {
    
    private Long dongCode;
    
    private String dongName;
}
