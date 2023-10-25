package com.plonit.plonitservice.api.auth.controller;

import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.auth.controller.response.LogInUrlRes;
import com.plonit.plonitservice.api.auth.service.AuthService;
import com.plonit.plonitservice.common.CustomApiResponse;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.plonit.plonitservice.common.util.LogCurrent.*;

@RequestMapping("/plonit-service/auth")
@Tag(name = "Test", description = "설명")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;

    @Operation(summary = "get test", description = "test설정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK")
    })
    @GetMapping("/test")
    public String check(HttpServletRequest request) {
        log.info("server port = {}", request.getServerPort());
        return "Hi, there. This is a message from plonit-service";
    }

    @GetMapping("/kakao") // kakao 로그인 url 생성
    public CustomApiResponse<Object> loginURL() {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInUrlRes logInUrlRes = authService.getKakaoLogin();
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(logInUrlRes);
    }

    @GetMapping("/kakao/callback") // redirect url
    public CustomApiResponse<Object> kakaoCallBack(HttpServletRequest request) throws Exception {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInRes logInRes = authService.getKakaoInfo(request.getParameter("code"));
        log.info(logCurrent(getClassName(), getMethodName(), END));
        // 이미 로그인 했다면 바로 return
        return CustomApiResponse.ok(logInRes);
    }

    @GetMapping("/kakao/login") // redirect url
    public CustomApiResponse<Object> kakaoLogin(HttpServletRequest request) throws Exception {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInRes logInRes = authService.getKakaoInfo(request.getParameter("code"));
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(logInRes);
    }

    @PostMapping("/kakao/logout")
    public CustomApiResponse<Object> kakaoLogout(HttpServletRequest request) throws Exception {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInRes logInRes = authService.getKakaoInfo(request.getParameter("code"));
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(logInRes);
    }
}
