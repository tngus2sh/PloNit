import React from "react";
import style from "styles/css/LoginPage.module.css";

const Section_2 = () => {
  return (
    <div className={style.section2}>
      <div className={style.title}>
        <div>플로깅을 통해</div>
        <div>건강과 환경을 지켜요.</div>
      </div>
      <div className={style.content}>같은 시간 대비 조깅보다 좋은 운동효과</div>
      <img src="./login2.gif" alt="플로깅" />
    </div>
  );
};

export default Section_2;
