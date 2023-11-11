package com.plonit.plonitservice.api.auth.controller;

import com.plonit.plonitservice.api.auth.controller.response.CheckNicknameRes;
import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.auth.controller.response.LogInUrlRes;
import com.plonit.plonitservice.api.auth.controller.response.ReissueReq;
import com.plonit.plonitservice.api.auth.service.AuthService;
import com.plonit.plonitservice.api.fcm.service.FCMService;
import com.plonit.plonitservice.common.CustomApiResponse;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.plonit.plonitservice.common.exception.ErrorCode.INVALID_FIELDS_REQUEST;
import static com.plonit.plonitservice.common.util.LogCurrent.*;

@RequestMapping("/api/plonit-service/auth")
@Tag(name = "Auth API Controller", description = "Auth API Document")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private final FCMService fcmService;
    @Operation(summary = "Test", description = "test설정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/test")
    public String check(HttpServletRequest request) {
        log.info("server port = {}", request.getServerPort());
        return "Hi, there. This is a message from plonit-service";
    }

    @Operation(summary = "카카오 로그인 URL 발급", description = "카카오 로그인 URL 발급")
    @GetMapping("/kakao/url") // kakao 로그인 url 생성
    public CustomApiResponse<Object> kakaoLoginURL() {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInUrlRes logInUrlRes = authService.getKakaoLoginURL();
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(logInUrlRes);
    }

    @Operation(summary = "카카오 로그인", description = "카카오 로그인 후 토큰을 발급해줍니다.")
    @GetMapping("/kakao/login/{code}") // kakao 로그인 및 token 발급
    public CustomApiResponse<Object> kakaoToken(@PathVariable("code") String code, HttpServletResponse response) throws Exception {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        LogInRes logInRes = authService.getKakaoToken(response, code);
        fcmService.saveToken(logInRes);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(logInRes);
    }

    @Operation(summary = "닉네임 중복 체크", description = "닉네임을 중복 체크합니다.")
    @GetMapping("/check-nickname/{nickname}") // 닉네임 중복 체크
    public CustomApiResponse<Object> checkNickname(@PathVariable("nickname") String nickname) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        CheckNicknameRes checkNicknameRes = authService.checkNickname(nickname);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(checkNicknameRes);
    }

    @Operation(summary = "토큰 재발급", description = "인증된 사용자에게 토큰을 재발급해줍니다.")
    @GetMapping("/regenerate") // 토큰 재발급
    public CustomApiResponse<Object> regenerate(@Validated @RequestBody ReissueReq reissueReq, HttpServletResponse response, Errors errors) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(e -> {
                log.info("error message : " + e.getDefaultMessage());
            });
            log.info(logCurrent(getClassName(), getMethodName(), END));
            throw new CustomException(INVALID_FIELDS_REQUEST);
        }

        log.info(logCurrent(getClassName(), getMethodName(), END));
        authService.regenerate(reissueReq, response);
        return CustomApiResponse.ok("", "Token 정보가 갱신되었습니다.");
    }

    @Operation(summary = "카카오 로그아웃", description = "카카오 로그아웃을 합니다.")
    @PostMapping("/kakao/logout") // 로그아웃
    public CustomApiResponse<Object> kakaoLogout(HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        Long memberId = authService.kakaoLogout(request);
        fcmService.deleteToken(memberId);

        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok("", "로그아웃을 성공하셨습니다.");
    }
}
