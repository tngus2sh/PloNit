import React from "react";
import style from "styles/css/CrewpingDetailPage/CrewpingIntroduce.module.css";
import { CrewpingInterface } from "interface/crewInterface"; // 경로는 실제 경로에 맞게 조정해주세요.

interface CrewpingInfoProps {
  crewping: CrewpingInterface;
}

const CrewpingIntroduce = ({ crewping }: CrewpingInfoProps) => {
  return (
    <div className={style.crewping_introduce}>
      <div className={style.title}>크루 소개</div>
      <div className={style.content}>{crewping.introduce} </div>
    </div>
  );
};

export default CrewpingIntroduce;
