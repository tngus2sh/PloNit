package com.plonit.ploggingservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    @NonNull
    private final ErrorCode errorCode;
}