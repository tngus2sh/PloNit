package com.plonit.plonitservice.api.auth.controller;

import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.auth.controller.response.LogInUrlRes;
import com.plonit.plonitservice.api.auth.service.AuthService;
import com.plonit.plonitservice.common.ApiResponse;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static com.plonit.plonitservice.common.exception.ErrorCode.KAKAO_TOKEN_CONNECTED_FAIL;
import static com.plonit.plonitservice.common.exception.ErrorCode.USER_BAD_REQUEST;
import static com.plonit.plonitservice.common.util.LogCurrent.*;

@RequestMapping("/plonit-service/auth")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    @GetMapping("/test")
    public String check(HttpServletRequest request) {
        log.info("server port = {}", request.getServerPort());
        return "Hi, there. This is a message from plonit-service";
    }

    @GetMapping("/kakao") // kakao 로그인 url 생성
    public ApiResponse<Object> loginURL() {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInUrlRes logInUrlRes = authService.getKakaoLogin();
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return ApiResponse.ok(logInUrlRes);
    }

    @GetMapping("/kakao/callback") // redirect url
    public ApiResponse<Object> kakaoCallBack(HttpServletRequest request) throws Exception {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInRes logInRes = authService.getKakaoInfo(request.getParameter("code"));
        log.info(logCurrent(getClassName(), getMethodName(), END));
        // 이미 로그인 했다면 바로 return
        return ApiResponse.ok(logInRes);
    }

    @GetMapping("/kakao/login") // redirect url
    public ApiResponse<Object> kakaoLogin(HttpServletRequest request) throws Exception {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInRes logInRes = authService.getKakaoInfo(request.getParameter("code"));
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return ApiResponse.ok(logInRes);
    }

    @PostMapping("/kakao/logout")
    public ApiResponse<Object> kakaoLogout(HttpServletRequest request) throws Exception {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInRes logInRes = authService.getKakaoInfo(request.getParameter("code"));
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return ApiResponse.ok(logInRes);
    }
}
