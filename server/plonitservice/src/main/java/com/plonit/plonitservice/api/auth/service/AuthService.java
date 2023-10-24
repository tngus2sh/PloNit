package com.plonit.plonitservice.api.auth.service;

import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.auth.service.dto.KakaoDTO;
import com.plonit.plonitservice.api.auth.service.dto.TokenDTO;
import com.plonit.plonitservice.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.json.simple.JSONObject;

import static com.plonit.plonitservice.common.exception.ErrorCode.KAKAO_TOKEN_CONNECTED_FAIL;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RedisTemplate redisTemplate;

    @Value("${kakao.client.id}")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    public String getKakaoLogin() {
        return KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URL
                + "&response_type=code";
    }

    public LogInRes getKakaoAccessToken (String code) throws Exception {
        if (code == null) throw new CustomException(KAKAO_TOKEN_CONNECTED_FAIL);

        String accessToken = "";
        String refreshToken = "";

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type"   , "authorization_code");
            params.add("client_id"    , KAKAO_CLIENT_ID);
            params.add("client_secret", KAKAO_CLIENT_SECRET);
            params.add("code"         , code);
            params.add("redirect_uri" , KAKAO_REDIRECT_URL);

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<TokenDTO> response = restTemplate.exchange(
                    KAKAO_AUTH_URI + "/oauth/token",
                    HttpMethod.POST,
                    httpEntity,
                    TokenDTO.class
            );

            accessToken = response.getBody().getAccess_token();
            refreshToken = response.getBody().getRefresh_token();

        } catch (Exception e) {
            throw new CustomException(KAKAO_TOKEN_CONNECTED_FAIL);
        }
        return getUserInfoWithToken(accessToken);
    }

    private LogInRes getUserInfoWithToken(String accessToken) throws Exception {
        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<KakaoDTO> response = rt.exchange(
                KAKAO_API_URI + "/v2/user/me",
                HttpMethod.POST,
                httpEntity,
                KakaoDTO.class
        );

        KakaoDTO kakaoDTO = response.getBody();
        long id = kakaoDTO.getId();
//        String email = kakaoDTO.getKakao_account().getEmail();
//        String nickname = kakaoDTO.getKakao_account().getProfile().getNickname();

        return LogInRes.builder()
                .id(1)
                .email("ssafy")
                .nickname("ssafy").build();
    }

}
