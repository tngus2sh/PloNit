package com.plonit.ploggingservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Time {
    SEOUL("Asia/Seoul");
    
    public final String text;
    
}
