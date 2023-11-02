package com.plonit.plonitservice.api.auth.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class KakaoToken {
    private String kakaoAccessToken;
    private String kakaoRefreshToken;
    private Long kakaoAccessTokenExpiresIn;
    private Long kakaoRefreshTokenExpiresIn;

    public static KakaoToken of(String accessToken, String refreshToken,
                                long accessTokenExpiresIn, long refreshTokenExpiresIn) {
        return KakaoToken.builder()
                .kakaoAccessToken(accessToken)
                .kakaoRefreshToken(refreshToken)
                .kakaoAccessTokenExpiresIn(accessTokenExpiresIn)
                .kakaoRefreshTokenExpiresIn(refreshTokenExpiresIn)
                .build();
    }

    public static KakaoToken ofAccess(String accessToken,
                                      long accessTokenExpiresIn) {
        return KakaoToken.builder()
                .kakaoAccessToken(accessToken)
                .kakaoAccessTokenExpiresIn(accessTokenExpiresIn)
                .build();
    }

}