package com.plonit.ploggingservice.api.plogging.controller.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SidoGugunCodeRes {
    
    private Long sidoCode;
    
    private Long gugunCode;
    
}
