package com.plonit.plonitservice.common.security;

import com.plonit.plonitservice.api.auth.controller.response.TokenInfoRes;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Environment env;
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 12 * 60 * 60 * 1000L;           // 12시간
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 60 * 24 * 60 * 60 * 1000L;    // 60일

    private final Key key;

    public JwtTokenProvider(Environment env) {
        this.env = env;
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("jwt.secret"));
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenInfoRes generateToken(long memberKey) {

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        Date refreshTokenExpiredIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        String subject = String.valueOf(memberKey);

        String accessToken = Jwts.builder()
                .setSubject(subject)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
//                .setSubject(subject)
                .setExpiration(refreshTokenExpiredIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return TokenInfoRes.of(accessToken, refreshToken, ACCESS_TOKEN_EXPIRE_TIME, REFRESH_TOKEN_EXPIRE_TIME);
    }

}