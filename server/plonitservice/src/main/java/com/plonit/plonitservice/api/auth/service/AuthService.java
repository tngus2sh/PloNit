package com.plonit.plonitservice.api.auth.service;

import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.auth.controller.response.LogInUrlRes;
import com.plonit.plonitservice.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


import static com.plonit.plonitservice.common.exception.ErrorCode.KAKAO_TOKEN_CONNECTED_FAIL;

@Service
public class AuthService {
    private final RedisTemplate redisTemplate;
    private final Environment env;

    private String KAKAO_CLIENT_ID;

    private String KAKAO_CLIENT_SECRET;

    private String KAKAO_REDIRECT_URL;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    public AuthService(RedisTemplate redisTemplate, Environment env) {
        this.redisTemplate = redisTemplate;
        this.env = env;
        this.KAKAO_CLIENT_ID = env.getProperty("kakao.client.id");
        this.KAKAO_CLIENT_SECRET = env.getProperty("kakao.client.secret");
        this.KAKAO_REDIRECT_URL = env.getProperty("kakao.redirect.url");
    }

    public LogInUrlRes getKakaoLogin() {
        return LogInUrlRes.builder()
                .url(KAKAO_AUTH_URI + "/oauth/authorize"
                        + "?client_id=" + KAKAO_CLIENT_ID
                        + "&redirect_uri=" + KAKAO_REDIRECT_URL
                        + "&response_type=code").build();
    }

    public LogInRes getKakaoInfo (String code) throws Exception {
        if (code == null) throw new CustomException(KAKAO_TOKEN_CONNECTED_FAIL);

        String accessToken = "";
        //todo : redis
        String refreshToken = "";
        long expiresIn = 0;
        long refreshTokenExpiresIn = 0;

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type"   , "authorization_code");
            params.add("client_id"    , KAKAO_CLIENT_ID);
            params.add("redirect_uri" , KAKAO_REDIRECT_URL);
            params.add("code"         , code);
            params.add("client_secret", KAKAO_CLIENT_SECRET);


            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    KAKAO_AUTH_URI + "/oauth/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());

            accessToken  = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");
            expiresIn = (long) jsonObj.get("expires_in");
            refreshTokenExpiresIn = (long) jsonObj.get("refresh_token_expires_in");
            System.out.println(accessToken);

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
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URI + "/v2/user/me",
                HttpMethod.POST,
                httpEntity,
                String.class
        );
        System.out.println("getUserInfoWithToken : " + response);
        //Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj    = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile = (JSONObject) account.get("profile");

        long id = (long) jsonObj.get("id");
        String email = String.valueOf(account.get("email"));
        String nickname = String.valueOf(profile.get("nickname"));
        String profileImage = String.valueOf(profile.get("profile_image_url"));

        return LogInRes.builder()
                .email(email)
                .profileImage(profileImage).build();
    }

}
