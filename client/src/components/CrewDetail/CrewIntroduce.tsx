import React from "react";
import style from "styles/css/CrewDetailPage/CrewIntroduce.module.css";
import { CrewInterface } from "interface/crewInterface";

const CrewIntroduce = ({ crew }: { crew: CrewInterface }) => {
  return (
    <div className={style.crew_introduce}>
      <div className={style.title}>크루 소개</div>
      <div className={style.content}>{crew.introduce}</div>
    </div>
  );
};

export default CrewIntroduce;
