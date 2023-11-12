package com.plonit.ploggingservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Type {
    
    CREWPING("크루핑"),
    IND("개인"),
    VOL("자원봉사");
    
    
    private final String text;
    
    
}
