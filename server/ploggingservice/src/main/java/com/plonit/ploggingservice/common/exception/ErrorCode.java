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
    USER_BAD_REQUEST(HttpStatus.BAD_REQUEST, "해당하는 유저가 존재하지 않습니다."),
    INVALID_FIELDS_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 필드입니다."),
    INVALID_PLACE_REQUEST(HttpStatus.BAD_REQUEST, "해당하는 위치가 존재하지 않습니다."),
    PLOGGING_BAD_REQUEST(HttpStatus.BAD_REQUEST, "해당하는 플로깅이 존재하지 않습니다."),
    INVALID_CREWPINGID_REQUEST(HttpStatus.BAD_REQUEST, "해당하는 크루핑이 존재하지 않습니다."),
    PLOGGING_HELP_BAD_REQUEST(HttpStatus.BAD_REQUEST, "해당하는 도움이 존재하지 않습니다."),

    /* 401 UNAUTHORIZED */

    /* 403 FORBIDDEN : 페이지 접근 거부 */

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */


    /* 500 INTERNAL_SERVER_ERROR : 서버 내부 로직 에러 */
    KAKAO_ADDRESS_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "카카오 서버 오류입니다."),
    INVALID_MAKING_EXCEL(HttpStatus.BAD_REQUEST, "엑셀 파일을 생성하는 중 문제가 발생했습니다."),
    INVALID_SEND_EMAIL(HttpStatus.BAD_REQUEST, "메일을 전송하는 중 문제가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String description;
}