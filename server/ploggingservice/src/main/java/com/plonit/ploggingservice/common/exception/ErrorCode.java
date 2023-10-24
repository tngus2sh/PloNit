package com.plonit.ploggingservice.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST */
    REFRESH_TOKEN_MISMATCH(HttpStatus.BAD_REQUEST, "Refresh Token 정보가 일치하지 않습니다."),
    REFRESH_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "Refresh Token 정보가 유효하지 않습니다."),
    ACCESS_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "Access Token 유효하지 않은 토큰입니다."),
    USER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "해당하는 유저가 존재하지 않습니다.");

    /* 401 UNAUTHORIZED */

    /* 403 FORBIDDEN : 페이지 접근 거부 */

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */


    /* 500 INTERNAL_SERVER_ERROR : 서버 내부 로직 에러 */

    private final HttpStatus httpStatus;
    private final String description;
}