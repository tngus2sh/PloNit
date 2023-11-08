import React from "react";
import style from "styles/css/CrewDetailPage/CrewInfo.module.css";
import { Icon } from "@iconify/react";
import { CrewInterface } from "interface/crewInterface";

const CrewInfo = ({ crew }: { crew: CrewInterface }) => {
  return (
    <div className={style.crew_info}>
      <div className={style.title}>크루 정보</div>
      <div className={style.content}>
        <Icon icon="bi:geo-alt" className={style.icon} />
        <div>{crew.region}</div>
      </div>
      {/* <div className={style.content}>
        <Icon icon="bi:paypal" className={style.icon} />
        <div>124 회</div>
      </div> */}
      <div className={style.content}>
        <Icon icon="bi:person-fill" className={style.icon} />
        <div>{crew.cntPeople} 명</div>
      </div>
    </div>
  );
};

export default CrewInfo;
