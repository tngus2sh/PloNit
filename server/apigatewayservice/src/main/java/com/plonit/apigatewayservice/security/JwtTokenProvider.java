package com.plonit.apigatewayservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.security.Key;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Environment env;
    private static final String AUTHORIZATION_HEADER = "AccessToken";
    private static final String BEARER_TYPE = "Bearer";

    private final Key key;

    private final RedisTemplate redisTemplate;

    public JwtTokenProvider(RedisTemplate redisTemplate, Environment env) {
        this.redisTemplate = redisTemplate;
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        this.env = env;
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("jwt.secret"));
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Header 에서 토큰 정보 추출 메서드
    public String resolveToken(ServerHttpRequest request) {
        String bearerToken = request.getHeaders().get(AUTHORIZATION_HEADER).get(0);
//        log.info("bearerToken - " + bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) throws Exception{
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);   // ACCESS_TOKEN_INVALID
            throw new Exception("ACCESS_TOKEN_INVALID");
        } catch (ExpiredJwtException e) {       // UNKNOWN_ERROR
            log.info("Expired JWT Token", e);
            throw new Exception("UNKNOWN_ERROR");
        } catch (UnsupportedJwtException e) {   // UNKNOWN_ERROR
            log.info("UNKNOWN_ERROR", e);
            throw new Exception("ACCESS_TOKEN_INVALID");
        } catch (IllegalArgumentException e) {  // UNKNOWN_ERROR
            log.info("JWT claims string is empty.", e);
            throw new Exception("UNKNOWN_ERROR");
        }
    }

    // refresh 토큰 정보를 검증하는 메서드
    public boolean validateRefreshToken(long memberKey) {
        String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + memberKey);
        if (!ObjectUtils.isEmpty(refreshToken)) return true;
        return false;

    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public long getAuthentication(String accessToken) throws Exception {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);
        // UserDetails 객체를 만들어서 Authentication 리턴
        return Long.parseLong(claims.getSubject());
    }

    private Claims parseClaims(String accessToken) throws Exception{
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            throw new Exception("ExpiredJwtException");
        }
    }

//    private boolean isValidMemberKey(String memberKey, String token) {
//        try {
//            long id = Long.parseLong(memberKey);
//            String accessToken = (String) redisTemplate.opsForValue().get("AT:" + id);
//            String refreshToken = (String) redisTemplate.opsForValue().get("RT:" + id);
//            if (!ObjectUtils.isEmpty(accessToken) && !ObjectUtils.isEmpty(refreshToken) ) {
//                if(accessToken.equals(token)) {
//                    return true;
//                }
//            }
//            return false;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
}
