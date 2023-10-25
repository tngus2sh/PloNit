package com.plonit.plonitservice.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomApiResponse<T> {

    private DataHeader resultStatus;
    private T resultBody;

    public CustomApiResponse(DataHeader dataHeader, T dataBody) {
        this.resultStatus = dataHeader;
        this.resultBody = dataBody;
    }

    public static <T> CustomApiResponse<T> of(int successCode, HttpStatus status, String resultMessage) {
        return new CustomApiResponse<>(new DataHeader(successCode, String.valueOf(status.value()), resultMessage), null);
    }

    public static <T> CustomApiResponse<T> ok(T data) {
        return new CustomApiResponse<>(new DataHeader(0, "", ""), data);
    }
}