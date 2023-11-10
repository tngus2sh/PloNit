import React from "react";
import style from "styles/css/CrewpingDetailPage/CrewpingLeader.module.css";
import { CrewpingInterface } from "interface/crewInterface"; // 경로는 실제 경로에 맞게 조정해주세요.

interface CrewpingInfoProps {
  crewping: CrewpingInterface;
}

const CrewpingLeader = ({ crewping }: CrewpingInfoProps) => {
  const imageUrl = crewping.crewpingImage || "/metamong.png"; // crewpingImage가 없는 경우 기본 이미지로 대체합니다.

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
