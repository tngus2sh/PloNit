package com.plonit.plonitservice.common.security;

import com.plonit.plonitservice.api.auth.controller.response.TokenInfoRes;
import com.plonit.plonitservice.common.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

import static com.plonit.plonitservice.common.exception.ErrorCode.REFRESH_TOKEN_INVALID;
import static com.plonit.plonitservice.common.exception.ErrorCode.WRONG_TYPE_TOKEN;

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

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);   // ACCESS_TOKEN_INVALID
            return false;
        } catch (ExpiredJwtException e) {       // UNKNOWN_ERROR
            log.info("Expired JWT Token", e);
            return false;
        } catch (UnsupportedJwtException e) {   // UNKNOWN_ERROR
            log.info("UNKNOWN_ERROR", e);
            return false;
        } catch (IllegalArgumentException e) {  // UNKNOWN_ERROR
            log.info("JWT claims string is empty.", e);
            return false;
        }
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public long getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.getSubject() == null) {
            throw new CustomException(WRONG_TYPE_TOKEN);
        }
        // UserDetails 객체를 만들어서 Authentication 리턴
        return Long.parseLong(claims.getSubject());
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

}