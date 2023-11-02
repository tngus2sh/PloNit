import React from "react";
import style from "styles/css/LoginPage.module.css";

const Section_2 = () => {
  return (
    <div className={style.section2}>
      <div className={style.content}>
        <div>플로깅을 통해</div>
        <div>건강과 환경을 지켜요.</div>
      </div>
      <img src="./plogging.jpg" alt="플로깅" />
    </div>
  );
};

export default Section_2;
