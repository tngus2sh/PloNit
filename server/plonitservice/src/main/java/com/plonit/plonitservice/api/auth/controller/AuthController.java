package com.plonit.plonitservice.api.auth.controller;

import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.auth.service.AuthService;
import com.plonit.plonitservice.common.ApiResponse;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static com.plonit.plonitservice.common.exception.ErrorCode.KAKAO_TOKEN_CONNECTED_FAIL;
import static com.plonit.plonitservice.common.exception.ErrorCode.USER_BAD_REQUEST;

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

    @GetMapping("/kakao") // kakao redirect url 생성
    public String login() {
        return authService.getKakaoLogin();
    }

    @GetMapping("/kakao/callback") // redirect url
    public ApiResponse<Object> kakaoLogin(@RequestParam(required = false) String code) throws Exception {
        LogInRes logInRes = authService.getKakaoInfo(code);
        return ApiResponse.ok(logInRes);
    }
}
