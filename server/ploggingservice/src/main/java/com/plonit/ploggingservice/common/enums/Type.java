package com.plonit.ploggingservice.common.enums;

public enum Type {
    
    CREWPING("크루핑"),
    IND("개인"),
    VOL("자원봉사");
    
    
    private final String text;
    
    Type(String text) {
        this.text = text;
    }
    
    
}
