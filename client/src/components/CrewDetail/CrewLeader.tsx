import React from "react";
import style from "styles/css/CrewLeader.module.css";

const CrewLeader = () => {
  return (
    <div className={style.crew_leader}>
      <div className={style.title}>크루 리더</div>
      <div className={style.content}>
        <img src="/metamong.png" alt="몽" />
        <div className={style.nickname}>내가 대장</div>
      </div>
    </div>
  );
};

export default CrewLeader;
