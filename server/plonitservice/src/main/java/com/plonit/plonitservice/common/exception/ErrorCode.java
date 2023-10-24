package com.plonit.plonitservice.common.exception;

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
    USER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "해당하는 유저가 존재하지 않습니다."),

    /* 401 UNAUTHORIZED */
    UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "인증 Token 이 존재하지 않습니다."),       // JWTFilterException
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 Access Token 정보입니다."),    // JWTFilterException
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 Access Token 입니다."),          // JWTFilterException
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원하지 않는 Token 입니다."),       // JWTFilterException
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "알 수 없는 이유로 요청이 거절되었습니다."), //JWTFilterException
    NOT_LOGIN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인 후 이용해주세요."),

    /* 403 FORBIDDEN : 페이지 접근 거부 */

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    USER_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 가입되어 있는 유저입니다."),
    ID_ALREADY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),


    /* 500 INTERNAL_SERVER_ERROR : 서버 내부 로직 에러 */
    SSE_CONNECTED_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "SSE 연결이 실패하였습니다."),
    KAKAO_TOKEN_CONNECTED_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 토큰 발급에 실패하였습니다.");

    private final HttpStatus httpStatus;
    private final String description;
}