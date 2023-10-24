import React from "react";
import style from "styles/css/CrewInfo.module.css";
import { Icon } from "@iconify/react";

const CrewInfo = () => {
  return (
    <div className={style.crew_info}>
      <div className={style.title}>크루 정보</div>
      <div className={style.content}>
        <Icon icon="bi:geo-alt" className={style.icon} />
        <div>광주 광산구 장덕동</div>
      </div>
      <div className={style.content}>
        <Icon icon="bi:paypal" className={style.icon} />
        <div>124 회</div>
      </div>
      <div className={style.content}>
        <Icon icon="bi:person-fill" className={style.icon} />
        <div>41 명</div>
      </div>
    </div>
  );
};

export default CrewInfo;
