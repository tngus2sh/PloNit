import React from "react";
import style from "styles/css/LoginPage.module.css";

const Section_3 = () => {
  return (
    <div className={style.section3}>
      <div className={style.title}>
        <div>혼자 또는 사람들과 함께</div>
        <div>플로깅을 해보는 건 어떨까요?</div>
      </div>
      <div className={style.content}>지금 바로 플로깅에 참여하세요</div>
      <img src="./login1.gif" alt="플로깅" />
    </div>
  );
};

export default Section_3;
