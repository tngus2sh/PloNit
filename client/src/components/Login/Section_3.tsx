import React from "react";
import style from "styles/css/LoginPage.module.css";

const Section_3 = () => {
  return (
    <div className={style.section3}>
      <div className={style.content}>
        <div>혼자 또는 사람들과 함께</div>
        <div>플로깅을 해보는 건 어떨까요?</div>
      </div>
      <img src="./plogging.jpg" alt="플로깅" />
    </div>
  );
};

export default Section_3;
