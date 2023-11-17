import React from "react";
import style from "styles/css/CrewDetailPage/CrewLeader.module.css";
import { CrewInterface } from "interface/crewInterface";

const CrewLeader = ({ crew }: { crew: CrewInterface }) => {
  return (
    <div className={style.crew_leader}>
      <div className={style.title}>크루 리더</div>
      <div className={style.content}>
        <img src={crew.crewMasterProfileImage} alt="몽" />
        <div className={style.nickname}>{crew.crewMasterNickname}</div>
      </div>
    </div>
  );
};

export default CrewLeader;
