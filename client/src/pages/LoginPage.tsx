import React, { useState, useEffect } from "react";
import style from "styles/css/LoginPage.module.css";
import Section_1 from "components/Login/Section_1";
import Section_2 from "components/Login/Section_2";
import Section_3 from "components/Login/Section_3";
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination } from "swiper/modules";

import "swiper/css";
import "swiper/css/pagination";
import { useDispatch } from "react-redux";
import { userActions } from "store/user-slice";
import { registerServiceWorker } from "notification";
import { messaging } from "settingFCM";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
import styled from "styled-components";

const StyledSwiper = styled(Swiper)`
  .swiper-pagination {
    position: relative;
  }
  .swiper-pagination-bullet-active {
    background: #2cd261;
  }
`;

async function handleAllowNotification() {
  const permission = await Notification.requestPermission();

  registerServiceWorker();
}

const LoginPage = () => {
  const dispatch = useDispatch();
  const client_id = process.env.REACT_APP_CLIENT_ID;
  const redirect_uri = process.env.REACT_APP_REDIRECT_URI;
  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${client_id}&redirect_uri=${redirect_uri}&response_type=code`;
  const handleLogin = () => {
    window.location.href = KAKAO_AUTH_URL;
  };
  // const [deviceToken, setDeviceToken] = useState("");
  // async function getDeviceToken() {
  //   try {
  //     const token = await getToken(messaging, {
  //       vapidKey: process.env.REACT_APP_VAPID_KEY,
  //     });
  //     setDeviceToken(token);
  //     const data = { fcmToken: token };
  //     dispatch(userActions.fcmHandler(data));
  //   } catch (error) {
  //     console.error("디바이스 토큰 발급 실패:", error);
  //     const data = { fcmToken: "" };
  //     dispatch(userActions.fcmHandler(data));
  //   }
  // }
  // getDeviceToken();
  // console.log(deviceToken);
  return (
    <div className={style.login}>
      <StyledSwiper
        pagination={true}
        modules={[Pagination]}
        className={style.myswiper}
      >
        <SwiperSlide>
          <Section_1 />
        </SwiperSlide>
        <SwiperSlide>
          <Section_2 />
        </SwiperSlide>
        <SwiperSlide>
          <Section_3 />
        </SwiperSlide>
      </StyledSwiper>
      <div onClick={handleLogin} className={style.login_btn}>
        <img src="/kakao_login2.png" alt="카카오" style={{ width: "90%" }} />
      </div>
    </div>
  );
};

export default LoginPage;
