package com.plonit.plonitservice.api.auth.service;

import com.plonit.plonitservice.api.auth.controller.response.CheckNicknameRes;
import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.auth.controller.response.LogInUrlRes;
import com.plonit.plonitservice.api.auth.controller.response.TokenInfoRes;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.plonit.plonitservice.common.exception.ErrorCode.*;

@Service
@Slf4j
public class AuthService {
    private final RedisTemplate redisTemplate;
    private final Environment env;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;
    private static final String AUTHORIZATION_HEADER = "AccessToken";
    private static final String BEARER_TYPE = "Bearer";

    private String KAKAO_CLIENT_ID;

    private String KAKAO_CLIENT_SECRET;

    private String KAKAO_REDIRECT_URL;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    public AuthService(RedisTemplate redisTemplate, Environment env, MemberQueryRepository memberQueryRepository, MemberRepository memberRepository) {
        this.redisTemplate = redisTemplate;
        this.env = env;
        this.KAKAO_CLIENT_ID = env.getProperty("kakao.client.id");
        this.KAKAO_CLIENT_SECRET = env.getProperty("kakao.client.secret");
        this.KAKAO_REDIRECT_URL = env.getProperty("kakao.redirect.url");
        this.memberQueryRepository = memberQueryRepository;
        this.memberRepository = memberRepository;
    }

    public LogInUrlRes getKakaoLoginURL() {
        return LogInUrlRes.of(KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URL
                + "&response_type=code");
    }

    public LogInRes getKakaoToken (HttpServletResponse httpServletResponse, String code){
        String accessToken = "";
        String refreshToken = "";
        long accessTokenExpiresIn = 0;
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
            accessTokenExpiresIn = (long) jsonObj.get("expires_in");
            refreshTokenExpiresIn = (long) jsonObj.get("refresh_token_expires_in");
            System.out.println("accessToken :" + accessToken);
            System.out.println("refreshToken :" + refreshToken);
            System.out.println("accessTokenExpiresIn :" + accessTokenExpiresIn);
            System.out.println("refreshTokenExpiresIn :" + refreshTokenExpiresIn);

        } catch (Exception e) {
            throw new CustomException(KAKAO_TOKEN_CONNECTED_FAIL);
        }
        LogInRes logInRes = getUserInfoWithToken(httpServletResponse, accessToken, refreshToken, accessTokenExpiresIn, refreshTokenExpiresIn);
        return logInRes;
    }

    private LogInRes getUserInfoWithToken(HttpServletResponse httpServletResponse,
                                      String accessToken, String refreshToken,
                                      long accessTokenExpiresIn, long refreshTokenExpiresIn){
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
        JSONObject jsonObj    = null;
        try {
            jsonObj = (JSONObject) jsonParser.parse(response.getBody());
        } catch (ParseException e) {
            throw new CustomException(KAKAO_INFO_CONNECTED_FAIL);
        }
        JSONObject account = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile = (JSONObject) account.get("profile");

        long kakaoId = (long) jsonObj.get("id");

        Optional<Member> member = memberRepository.findByKakaoId(kakaoId);
        TokenInfoRes tokenInfoRes;

        if(member.isPresent()) { // 기존 유저
            tokenInfoRes = generateToken(member.get().getId(), accessToken, refreshToken, accessTokenExpiresIn, refreshTokenExpiresIn);
            setHeader(httpServletResponse, tokenInfoRes);
            return LogInRes.builder()
                    .id(member.get().getId())
                    .registeredMember(true)
                    .build();

        } else { // 신규 유저
            String email = String.valueOf(account.get("email"));
            String profileImage = String.valueOf(profile.get("profile_image_url"));
//            String nickname = String.valueOf(profile.get("nickname"));
            Member newMember = memberRepository.save(Member.builder().kakaoId(kakaoId).email(email).profileImage(profileImage).build());
            tokenInfoRes = generateToken(newMember.getId(), accessToken, refreshToken, accessTokenExpiresIn, refreshTokenExpiresIn);
            setHeader(httpServletResponse, tokenInfoRes);
            return LogInRes.builder()
                    .id(newMember.getId())
                    .registeredMember(false)
                    .build();
        }
    }
    public TokenInfoRes generateToken(long id, String accessToken, String refreshToken, long accessTokenExpiresIn, long refreshTokenExpiresIn) {
//        long now = (new Date()).getTime();
//        Date accessTokenExpiresTime = new Date(now + accessTokenExpiresIn);
//        Date refreshTokenExpiresTime = new Date(now + refreshTokenExpiresIn);

        // todo : redis 확인
        redisTemplate.opsForValue()
                .set("AT:" + id, accessToken, accessTokenExpiresIn, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue()
                .set("RT:" + id, refreshToken, refreshTokenExpiresIn, TimeUnit.MILLISECONDS);

        return TokenInfoRes.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .refreshTokenExpiresIn(refreshTokenExpiresIn)
                .build();
    }

    public boolean kakaoLogout(HttpServletRequest request) {
        String accessToken = resolveToken(request);

        //HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URI + "/v2/user/logout",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        System.out.println("getUserInfoWithToken : " + response);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj    = null;
        try {
            jsonObj = (JSONObject) jsonParser.parse(response.getBody());
        } catch (ParseException e) {
            throw new CustomException(KAKAO_LOGOUT_CONNECTED_FAIL);
        }
        long kakaoId = (long) jsonObj.get("id");
        Member member = memberRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new CustomException(USER_BAD_REQUEST));

        if (redisTemplate.opsForValue().get("AT:" + member.getId()) != null &&
                redisTemplate.opsForValue().get("RT:" + member.getId()) != null)  {
            redisTemplate.delete("AT:" + member.getId());
            redisTemplate.delete("RT:" + member.getId());
            return true;
        } else
            throw new CustomException(UNKNOWN_ERROR);

    }
    private void setHeader(HttpServletResponse response, TokenInfoRes tokenInfo) {
        response.addHeader("accessToken", tokenInfo.getAccessToken());
        response.addHeader("refreshToken", tokenInfo.getRefreshToken());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public CheckNicknameRes checkNickname(String nickname) {
        if(memberQueryRepository.existNickname(nickname))
            return CheckNicknameRes.of(false);
        return CheckNicknameRes.of(true);
    }

}
