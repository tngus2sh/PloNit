import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";

import { Icon } from "@iconify/react";
import style from "styles/css/CrewpingDetailPage/CrewpingInfo.module.css";

import { getCrewpingInfo } from "api/lib/crewping";

const CrewpingInfo = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  //const [totalCrewList, setTotalCrewList] = useState<CrewInterface[]>([]);
  const [inputValue, setInputValue] = useState<string>("");

  return (
    <div className={style.crewping_info}>
      <div className={style.title}>활동 내용</div>
      <div className={style.content}>
        <Icon icon="bi:calendar-check" className={style.icon} />
        <div>10.27(금) 17:00 ~ 18:30</div>
      </div>
      <div className={style.content}>
        <Icon icon="bi:geo-alt" className={style.icon} />
        <div>수완</div>
      </div>
      <div className={style.content}>
        <Icon icon="bi:person-fill" className={style.icon} />
        <div>5/8 명</div>
      </div>
    </div>
  );
};

export default CrewpingInfo;
