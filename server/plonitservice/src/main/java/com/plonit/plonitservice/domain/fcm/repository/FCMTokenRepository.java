package com.plonit.plonitservice.domain.fcm.repository;

import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FCMTokenRepository {

    private final StringRedisTemplate tokenRedisTemplate;

    public void saveFcmToken(Long memberId, String fcmToken) {
        tokenRedisTemplate.opsForValue()
                .set("FCM:" + memberId, fcmToken);
    }

    public String getFcmToken(Long memberId) {
        return tokenRedisTemplate.opsForValue().get("FCM:" + memberId);
    }

    public void deleteToken(Long memberId) {
        tokenRedisTemplate.delete("FCM:" + memberId);
    }

    public boolean hasKey(Long memberId) {
        return tokenRedisTemplate.hasKey("FCM:" + memberId);
    }
}