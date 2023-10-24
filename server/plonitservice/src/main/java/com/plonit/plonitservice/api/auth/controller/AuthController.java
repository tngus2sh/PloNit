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

    @GetMapping("/kakao") // kakao redirect_uri 생성
    public String login() {
        return authService.getKakaoLogin();
    }

    @GetMapping("/kakao/callback") // redirect url
    public ApiResponse<Object> kakaoLogin(@RequestParam(required = false) String code) {
        try {
            // URL에 포함된 code를 이용하여 액세스 토큰 발급
            String accessToken = authService.getKakaoAccessToken(code);
            System.out.println(accessToken);

            // 액세스 토큰을 이용하여 카카오 서버에서 유저 정보(닉네임, 이메일) 받아오기
            HashMap<String, Object> userInfo = authService.getUserInfo(accessToken);
            System.out.println("login Controller : " + userInfo);

            LogInRes logInRes = null;

            // 만일, DB에 해당 email을 가지는 유저가 없으면 회원가입 시키고 유저 식별자와 JWT 반환
            if(!memberQueryRepository.exists(String.valueOf(userInfo.get("email")))) {
                return ApiResponse.ok(logInRes);
            } else {
                // 아니면 기존 유저의 로그인으로 판단하고 유저 식별자와 JWT 반환
                Member member = memberRepository.findByEmail(String.valueOf(userInfo.get("email")))
                        .orElseThrow(() -> new CustomException(USER_BAD_REQUEST));
                logInRes = LogInRes.of(member);
                return ApiResponse.ok(logInRes);
            }
        } catch (Exception exception) {
            throw new CustomException(KAKAO_TOKEN_CONNECTED_FAIL);
        }
    }
}
