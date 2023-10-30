package com.plonit.ploggingservice.common.enums;

public enum Finished {
    
    ACTIVE("플로깅중"),
    FINISHED("완료");
    
    private final String text;
    
    Finished(String text) {
        this.text = text;
    }
    
}
