import React from "react";
import style from "styles/css/CrewpingDetailPage/CrewpingLeader.module.css";
import { CrewpingInterface } from "interface/crewInterface";

interface CrewpingInfoProps {
  crewping: CrewpingInterface;
}

const CrewpingLeader = ({ crewping }: CrewpingInfoProps) => {
  const imageUrl = crewping.masterImage || "/NoImage.png";

  return (
    <div className={style.crewping_leader}>
      <div className={style.title}>활동 리더</div>
      <div className={style.content}>
        <img src={imageUrl} alt={crewping.masterNickname || "리더 이미지"} />
        <div className={style.nickname}>{crewping.masterNickname}</div>
      </div>
    </div>
  );
};

export default CrewpingLeader;
