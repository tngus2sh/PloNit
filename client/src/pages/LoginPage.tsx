import React from "react";
import style from "styles/css/LoginPage.module.css";
import Section_1 from "components/Login/Section_1";
import Section_2 from "components/Login/Section_2";
import Section_3 from "components/Login/Section_3";
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination } from "swiper/modules";

import "swiper/css";
import "swiper/css/pagination";
import "custom_css/LoginSwiper.css";

const LoginPage = () => {
  const client_id = process.env.REACT_APP_CLIENT_ID;
  const redirect_uri = process.env.REACT_APP_REDIRECT_URI;
  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${client_id}&redirect_uri=${redirect_uri}&response_type=code`;
  const handleLogin = () => {
    window.location.href = KAKAO_AUTH_URL;
  };
  return (
    <div className={style.login}>
      <Swiper pagination={true} modules={[Pagination]} className="mySwiper">
        <SwiperSlide>
          <Section_1 />
        </SwiperSlide>
        <SwiperSlide>
          <Section_2 />
        </SwiperSlide>
        <SwiperSlide>
          <Section_3 />
        </SwiperSlide>
      </Swiper>
      <div onClick={handleLogin} className={style.login_btn}>
        <img src="/kakao_login2.png" alt="카카오" style={{ width: "90%" }} />
      </div>
    </div>
  );
};

export default LoginPage;
