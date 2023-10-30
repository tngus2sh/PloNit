package com.plonit.plonitservice.api.auth.service.impl;

import com.plonit.plonitservice.api.auth.controller.response.*;
import com.plonit.plonitservice.api.auth.service.AuthService;
import com.plonit.plonitservice.api.auth.service.DTO.KakaoToken;
import com.plonit.plonitservice.common.exception.CustomException;
import com.plonit.plonitservice.common.security.JwtTokenProvider;
import com.plonit.plonitservice.domain.member.Member;
import com.plonit.plonitservice.domain.member.repository.MemberQueryRepository;
import com.plonit.plonitservice.domain.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.plonit.plonitservice.common.exception.ErrorCode.*;
import static com.plonit.plonitservice.common.util.LogCurrent.*;
import static com.plonit.plonitservice.common.util.LogCurrent.START;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final RedisTemplate redisTemplate;
    private final Environment env;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberRepository memberRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private String KAKAO_CLIENT_ID;

    private String KAKAO_CLIENT_SECRET;

    private String KAKAO_REDIRECT_URL;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    public AuthServiceImpl(RedisTemplate redisTemplate, Environment env, MemberQueryRepository memberQueryRepository, MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.redisTemplate = redisTemplate;
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        this.env = env;
        this.KAKAO_CLIENT_ID = env.getProperty("kakao.client.id");
        this.KAKAO_CLIENT_SECRET = env.getProperty("kakao.client.secret");
        this.KAKAO_REDIRECT_URL = env.getProperty("kakao.redirect.url");
        this.memberQueryRepository = memberQueryRepository;
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LogInUrlRes getKakaoLoginURL() {
        return LogInUrlRes.of(KAKAO_AUTH_URI + "/oauth/authorize"
                + "?client_id=" + KAKAO_CLIENT_ID
                + "&redirect_uri=" + KAKAO_REDIRECT_URL
                + "&response_type=code");
    }

    public LogInRes getKakaoToken(HttpServletResponse httpServletResponse, String code) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        String accessToken = "";
        String refreshToken = "";
        long accessTokenExpiresIn = 0;
        long refreshTokenExpiresIn = 0;
        KakaoToken kakaoToken;

        // 1. HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("redirect_uri", KAKAO_REDIRECT_URL);
        params.add("code", code);
        params.add("client_secret", KAKAO_CLIENT_SECRET);


        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        // 2. HttpHeader 담기
        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_AUTH_URI + "/oauth/token",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // 3. kakao Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.parse(response.getBody());
        } catch (ParseException e) {
            throw new CustomException(KAKAO_INFO_CONNECTED_FAIL);
        }

        accessToken = (String) jsonObj.get("access_token");
        refreshToken = (String) jsonObj.get("refresh_token");
        accessTokenExpiresIn = (long) jsonObj.get("expires_in");
        refreshTokenExpiresIn = (long) jsonObj.get("refresh_token_expires_in");
//            System.out.println("kakao accessToken :" + accessToken);
//            System.out.println("kakao refreshToken :" + refreshToken);
        kakaoToken = KakaoToken.of(accessToken, refreshToken, accessTokenExpiresIn, refreshTokenExpiresIn);

        // 4. kakao token 발행 -> user 정보 찾기 -> token update
        LogInRes logInRes = getUserInfoWithToken(httpServletResponse, kakaoToken);
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return logInRes;
    }

    private LogInRes getUserInfoWithToken(HttpServletResponse httpServletResponse, KakaoToken kakaoToken) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        // 1. HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + kakaoToken.getKakaoAccessToken());
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 2. HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URI + "/v2/user/me",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // 3. kakao Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.parse(response.getBody());
        } catch (ParseException e) {
            throw new CustomException(KAKAO_INFO_CONNECTED_FAIL);
        }
        JSONObject account = (JSONObject) jsonObj.get("kakao_account");
        JSONObject profile = (JSONObject) account.get("profile");

        long kakaoId = (long) jsonObj.get("id");

        // 4. user find
        Optional<Member> member = memberRepository.findByKakaoId(kakaoId);

        if (member.isPresent()) {  // 5.1 기존 유저
            TokenInfoRes tokenInfoRes = generateToken(member.get().getId(), kakaoToken, false);
            setHeader(httpServletResponse, tokenInfoRes);
            log.info(logCurrent(getClassName(), getMethodName(), END));
            return LogInRes.builder()
                    .id(member.get().getId())
                    .registeredMember(true)
                    .tokenInfo(tokenInfoRes)
                    .build();

        } else { // 5.2 신규 유저
            String email = String.valueOf(account.get("email"));
            String profileImage = String.valueOf(profile.get("profile_image_url"));
            Member newMember = memberRepository.save(Member.builder().kakaoId(kakaoId).email(email).profileImage(profileImage).build());
            TokenInfoRes tokenInfoRes = generateToken(newMember.getId(), kakaoToken, false);
            setHeader(httpServletResponse, tokenInfoRes);
            log.info(logCurrent(getClassName(), getMethodName(), END));
            return LogInRes.builder()
                    .id(newMember.getId())
                    .registeredMember(false)
                    .tokenInfo(tokenInfoRes)
                    .build();
        }
    }

    private TokenInfoRes generateToken(long id, KakaoToken kakaoToken, boolean regenerate) {
        log.info(logCurrent(getClassName(), getMethodName(), START));
        // 1. token 재발급
        TokenInfoRes tokenInfoRes = jwtTokenProvider.generateToken(id);

        // 2. redis update
        redisTemplate.opsForValue()
                .set("RT:" + id, tokenInfoRes.getRefreshToken(), tokenInfoRes.getRefreshTokenExpiresIn(), TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue()
                .set("KAKAO_AT:" + id, kakaoToken.getKakaoAccessToken(), kakaoToken.getKakaoAccessTokenExpiresIn(), TimeUnit.SECONDS);
        if (!regenerate) {
            redisTemplate.opsForValue()
                    .set("KAKAO_RT:" + id, kakaoToken.getKakaoRefreshToken(), kakaoToken.getKakaoRefreshTokenExpiresIn(), TimeUnit.SECONDS);
        }
        log.info(logCurrent(getClassName(), getMethodName(), END));
        return tokenInfoRes;
    }

    public boolean kakaoLogout(HttpServletRequest request) {
        log.info(logCurrent(getClassName(), getMethodName(), START));

        // 1. user find
        Long id = Long.parseLong(request.getHeader("memberKey"));
        String accessToken = (String) redisTemplate.opsForValue().get("KAKAO_AT:" + id);

        if (ObjectUtils.isEmpty(accessToken)) {
            throw new CustomException(UNKNOWN_ERROR);
        }

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_BAD_REQUEST));

        // 2. HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("target_id_type", "user_id");
        params.add("target_id", String.valueOf(member.getKakaoId()));

        // 3. HttpHeader 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URI + "/v1/user/logout",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // 4. kakao Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.parse(response.getBody());
        } catch (ParseException e) {
            throw new CustomException(KAKAO_LOGOUT_CONNECTED_FAIL);
        }

        // 5. redis delete
        if (redisTemplate.opsForValue().get("RT:" + id) != null &&
                redisTemplate.opsForValue().get("KAKAO_RT:" + id) != null) {
            redisTemplate.delete("RT:" + id);
            redisTemplate.delete("KAKAO_AT:" + id);
            redisTemplate.delete("KAKAO_RT:" + id);
            log.info(logCurrent(getClassName(), getMethodName(), END));
            return true;
        } else
            throw new CustomException(UNKNOWN_ERROR);
    }

    @Transactional
    public void regenerate(ReissueReq reissue, HttpServletResponse httpServletResponse) {
        // 1. Refresh Token 검증
        if (!jwtTokenProvider.validateToken(reissue.getRefreshToken())) {
            throw new CustomException(REFRESH_TOKEN_INVALID);
        }

        // 2. AccessToken 에서 memberKey get
        long id = jwtTokenProvider.getAuthentication(reissue.getAccessToken());

        // 3. kakao Token 생성
        String kakaoRefreshToken = (String) redisTemplate.opsForValue().get("KAKAO_RT:" + id);

        String kakaoAccessToken = "";
        long kakaoAccessTokenExpiresIn = 0;
        KakaoToken kakaoToken;


        // 4. HttpHeader 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // 5. HttpHeader 담기
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", KAKAO_CLIENT_ID);
        params.add("refresh_token", kakaoRefreshToken);
        params.add("client_secret", KAKAO_CLIENT_SECRET);


        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                KAKAO_AUTH_URI + "/oauth/token",
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        // 6. kakao Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) jsonParser.parse(response.getBody());
        } catch (ParseException e) {
            throw new CustomException(KAKAO_LOGOUT_CONNECTED_FAIL);
        }

        kakaoAccessToken = (String) jsonObj.get("access_token");
        kakaoAccessTokenExpiresIn = (long) jsonObj.get("expires_in");
//            System.out.println("kakao accessToken :" + kakaoAccessToken);
//            System.out.println("kakao refreshToken :" + kakaoRefreshToken);
        kakaoToken = KakaoToken.ofAccess(kakaoAccessToken, kakaoAccessTokenExpiresIn);
        // 7. 새로운 토큰 생성 및 kakao Token 설정
        TokenInfoRes tokenInfoRes = generateToken(id, kakaoToken, true);
        setHeader(httpServletResponse, tokenInfoRes);

    }

    private void setHeader(HttpServletResponse response, TokenInfoRes tokenInfo) {
        response.addHeader("accessToken", tokenInfo.getAccessToken());
        response.addHeader("refreshToken", tokenInfo.getRefreshToken());
    }

    public CheckNicknameRes checkNickname(String nickname) {
        if (memberQueryRepository.existNickname(nickname))
            return CheckNicknameRes.of(false);
        return CheckNicknameRes.of(true);
    }

}
