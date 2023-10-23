import React from "react";
import style from "styles/css/CrewIntroduce.module.css";

const CrewIntroduce = () => {
  return (
    <div className={style.crew_introduce}>
      <div className={style.title}>크루 소개</div>
      <div className={style.content}>
        장덕동 주변에서 플로깅을 즐기는 크루입니다.
      </div>
    </div>
  );
};

export default CrewIntroduce;
