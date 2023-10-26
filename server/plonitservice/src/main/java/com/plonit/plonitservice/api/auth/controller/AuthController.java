package com.plonit.plonitservice.api.auth.controller;

import com.plonit.plonitservice.api.auth.controller.response.CheckNicknameRes;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @GetMapping("/kakao/url") // kakao 로그인 url 생성
    public CustomApiResponse<Object> kakaoLoginURL() {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInUrlRes logInUrlRes = authService.getKakaoLoginURL();
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(logInUrlRes);
    }

    @GetMapping("/kakao/login/{code}") // token 발급
    public CustomApiResponse<Object> kakaoToken(@PathVariable("code") String code, HttpServletResponse response) throws Exception {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        LogInRes logInRes = authService.getKakaoToken(response, code);
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(logInRes);
    }

    @GetMapping("/check-nickname/{nickname}") // 닉네임 중복 체크
    public CustomApiResponse<Object> checkNickname(@PathVariable("nickname") String nickname) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        CheckNicknameRes checkNicknameRes = authService.checkNickname(nickname);
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return CustomApiResponse.ok(checkNicknameRes);
    }

    @PostMapping("/kakao/logout") // 로그아웃
    public CustomApiResponse<Object> kakaoLogout(HttpServletRequest request){
        log.info(logCurrent(getClassName(), getMethodName(), START));
        authService.kakaoLogout(request);
        return CustomApiResponse.ok("", "로그아웃을 성공하셨습니다.");
    }
}
