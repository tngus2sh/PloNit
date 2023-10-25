import React from "react";
import style from "styles/css/CrewpingDetailPage/CrewpingIntroduce.module.css";

const CrewpingIntroduce = () => {
  return (
    <div className={style.crewping_introduce}>
      <div className={style.title}>크루 소개</div>
      <div className={style.content}>
        장덕동 주변에서 플로깅을 즐기는 크루입니다.
      </div>
    </div>
  );
};

export default CrewpingIntroduce;
