import React from "react";
import style from "styles/css/LoginPage.module.css";

const Section_1 = () => {
  return (
    <div className={style.section1}>
      <div className={style.title}>플로깅이란?</div>
      <div className={style.content}>
        <div>줍다(Plocka Upp)와 조깅(Jogging)의 합성어로, </div>
        <div>조깅하며 쓰레기를 줍다는 뜻을 가지고 있어요</div>
      </div>
      <img src="./plogging.jpg" alt="플로깅" />
    </div>
  );
};

export default Section_1;
