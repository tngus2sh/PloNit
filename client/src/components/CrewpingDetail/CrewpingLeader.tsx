import React from "react";
import style from "styles/css/CrewpingLeader.module.css";

const CrewpingLeader = () => {
  return (
    <div className={style.crewping_leader}>
      <div className={style.title}>활동 리더</div>
      <div className={style.content}>
        <img src="/metamong.png" alt="몽" />
        <div className={style.nickname}>내가 대장</div>
      </div>
    </div>
  );
};

export default CrewpingLeader;
