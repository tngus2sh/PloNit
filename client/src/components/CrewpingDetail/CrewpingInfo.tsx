import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";

import { Icon } from "@iconify/react";
import style from "styles/css/CrewpingDetailPage/CrewpingInfo.module.css";
import { CrewpingInterface } from "interface/crewInterface";
import CrewpingMemberList from "./CrewpingMemberList";

const CrewpingInfo = ({ crewping }: { crewping: CrewpingInterface }) => {
  return (
    <div className={style.crewping_info}>
      <div className={style.title}>활동 내용</div>
      <div className={style.content}>
        <Icon icon="bi:calendar-check" className={style.icon} />
        <div>
          {crewping.startDate} ~ {crewping.endDate}
        </div>
      </div>
      <div className={style.content}>
        <Icon icon="bi:geo-alt" className={style.icon} />
        <div>{crewping.place}</div>
      </div>
      <div className={style.content}>
        <Icon icon="bi:person-fill" className={style.icon} />
        <div>
          {crewping.cntPeople}/{crewping.maxPeople} 명
        </div>
        <CrewpingMemberList crewping={crewping} />
      </div>
    </div>
  );
};

export default CrewpingInfo;
