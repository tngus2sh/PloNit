import React from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import style from "styles/css/MyPloggingPage/MyPloggingItem.module.css";
import { PloggingLog } from "interface/ploggingInterface";
import CalendarMonthRoundedIcon from "@mui/icons-material/CalendarMonthRounded";
import FmdGoodRoundedIcon from "@mui/icons-material/FmdGoodRounded";
import AccessAlarmRoundedIcon from "@mui/icons-material/AccessAlarmRounded";

import testImg from "../../image/test.jpg";

const formattedDateTime = (datestr: any) => {
  const days = ["일", "월", "화", "수", "목", "금", "토"];
  const date = new Date(datestr);
  // const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const dayName = days[date.getDay()];
  const hour = date.getHours();
  const minute = date.getMinutes();
  const formattedDate = `${month}월 ${day}일(${dayName}) ${hour}:${
    minute < 10 ? "0" : ""
  }${minute}`;

  return formattedDate;
};

const findDayOfWeek = (datestr: any) => {
  const days = ["SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"];
  const date = new Date(datestr);

  return days[date.getDay()];
};

const findDay = (datestr: any) => {
  const date = new Date(datestr);

  return date.getDate();
};

const findTime = (datestr: any) => {
  const date = new Date(datestr);
  const hour = date.getHours();
  const minute = date.getMinutes();

  return `${hour}시 ${minute}분`;
};

const formatMinutes = (minutes: any) => {
  const hours = Math.floor(minutes / 60);
  const remainingMinutes = minutes % 60;
  return `${("0" + hours).slice(-2)}:${("0" + remainingMinutes).slice(-2)}`;
};

const MyPloggingItem = ({ plogging }: { plogging: PloggingLog }) => {
  const navigate = useNavigate();
  const User = useSelector((state: any) => state.user);
  console.log(plogging);

  const goPloggingDetailHandler = () => {
    navigate(`/profile/plogging/detail/${plogging.id}`);
  };
  return (
    <div className={style.myplogging_item} onClick={goPloggingDetailHandler}>
      <div className={style.left_area}>
        {/* <img src={User.info.profileImage} alt="프로필" /> */}
        {/* <img src={testImg} alt="프로필" /> */}
        <div className={style.week_container}>
          <div>{findDayOfWeek(plogging.startTime)}</div>
          <div className={style.day_container}>
            {findDay(plogging.startTime)}
          </div>
        </div>
      </div>
      <div style={{ width: "1rem" }}>
        <div className={style.v_line}></div>
      </div>
      <div className={style.right_area}>
        <div className={style.info_container}>
          <div className={style.type_container}>
            {plogging.type === "IND" ? (
              <div className={`${style.type} ${style.type_IND}`}>개인</div>
            ) : null}
            {plogging.type === "VOL" ? (
              <div className={`${style.type} ${style.type_VOL}`}>봉사</div>
            ) : null}
            {plogging.type === "CREWPING" ? (
              <div className={`${style.type} ${style.type_CREWPING}`}>크루</div>
            ) : null}
          </div>

          <div className={style.place_container}>
            <FmdGoodRoundedIcon sx={{ fontSize: "1.1rem" }} />
            &nbsp;{plogging.place}
          </div>
          {/* <div className={style.date_container}>
            <CalendarMonthRoundedIcon sx={{ fontSize: "1.1rem" }} />
            &nbsp;{formattedDateTime(plogging.startTime)}
          </div> */}

          {/* <div className={style.date_container}>
            <AccessAlarmRoundedIcon sx={{ fontSize: "1.1rem" }} />
            &nbsp;{findTime(plogging.startTime)}
          </div> */}
        </div>

        {/* <div className={style.place_container}>
          <FmdGoodRoundedIcon sx={{ fontSize: "1.1rem" }} />
          &nbsp;{plogging.place}
        </div> */}

        <div className={style.plogging_container}>
          <div className={style.distance_container}>
            거리 <span className={style.largeNumber}>{plogging.distance}</span>
            km
          </div>
          <div className={style.time_container}>
            시간{" "}
            <span className={style.largeNumber}>
              {formatMinutes(plogging.totalTime)}
            </span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyPloggingItem;
