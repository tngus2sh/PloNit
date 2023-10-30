package com.plonit.plonitservice.api.auth.service;

import com.plonit.plonitservice.api.auth.controller.response.CheckNicknameRes;
import com.plonit.plonitservice.api.auth.controller.response.LogInRes;
import com.plonit.plonitservice.api.auth.controller.response.LogInUrlRes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    LogInUrlRes getKakaoLoginURL();
    LogInRes getKakaoToken (HttpServletResponse httpServletResponse, String code);
    boolean kakaoLogout(HttpServletRequest request);
    CheckNicknameRes checkNickname(String nickname);

}
