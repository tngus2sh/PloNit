package com.plonit.plonitservice.api.region.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class DongRes {
    
    private Long dongCode;
    
    private Long dongName;
}
