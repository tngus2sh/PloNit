import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";

import { Icon } from "@iconify/react";
import style from "styles/css/CrewpingDetailPage/CrewpingInfo.module.css";
import { CrewpingInterface } from "interface/crewInterface";
import CrewpingMemberList from "./CrewpingMemberList";

const formattedDateTime = (datestr: any, onlyTime = false) => {
  const days = ["일", "월", "화", "수", "목", "금", "토"];
  const date = new Date(datestr);

  const month = date.getMonth() + 1;
  const day = date.getDate();
  const dayName = days[date.getDay()];
  const hour = date.getHours();

  const minute = date.getMinutes();

  const time = `${hour}:${minute < 10 ? "0" : ""}${minute}`;

  if (onlyTime) {
    return time;
  }

  const formattedDate = `${month}월 ${day}일(${dayName}) ${time}`;

  return formattedDate;
};

const CrewpingInfo = ({ crewping }: { crewping: CrewpingInterface }) => {
  const User = useSelector((state: any) => state.user);
  const [isMemberModalOpen, setMemberModalOpen] = useState(false);
  const StartDate = crewping.startDate;
  const EndDate = crewping.endDate;

  const toggleMember = () => {
    setMemberModalOpen(!isMemberModalOpen);
  };

  return (
    <div className={style.crewping_info}>
      <div className={style.title}>활동 내용</div>
      <div className={style.content}>
        <Icon icon="bi:calendar-check" className={style.icon} />
        <div>
          {StartDate && EndDate
            ? `${formattedDateTime(StartDate)} ~ ${formattedDateTime(
                EndDate,
                StartDate.slice(0, 10) === EndDate.slice(0, 10),
              )}`
            : "날짜 정보 없음"}
        </div>
      </div>
      <div className={style.content}>
        <Icon icon="bi:geo-alt" className={style.icon} />
        <div>{crewping.place}</div>
      </div>
      <div className={style.content} onClick={toggleMember}>
        <Icon icon="bi:person-fill" className={style.icon} />
        <div>
          {crewping.cntPeople}/{crewping.maxPeople} 명
        </div>
        {User.crewinfo.isMyCrew ? (
          isMemberModalOpen ? (
            <Icon
              icon="bi:chevron-up"
              className={style.icon}
              style={{ marginLeft: "0.2rem" }}
            />
          ) : (
            <Icon
              icon="bi:chevron-down"
              className={style.icon}
              style={{ marginLeft: "0.2rem" }}
            />
          )
        ) : null}
      </div>
      {isMemberModalOpen ? <CrewpingMemberList crewping={crewping} /> : null}
    </div>
  );
};

export default CrewpingInfo;
