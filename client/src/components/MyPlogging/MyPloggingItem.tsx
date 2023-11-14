import React from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import style from "styles/css/MyPloggingPage/MyPloggingItem.module.css";
import { PloggingLog } from "interface/ploggingInterface";

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
        <img src={User.info.profileImage} alt="프로필" />
      </div>
      <div className={style.right_area}>
        <div className={style.first_area}>
          {plogging.type === "IND" ? (
            <div className={style.divide_IND}>개인</div>
          ) : null}
          {plogging.type === "VOL" ? (
            <div className={style.divide_VOL}>봉사</div>
          ) : null}
          {plogging.type === "CREWPING" ? (
            <div className={style.divide_CREWPING}>크루</div>
          ) : null}
          <div className={style.date_place}>
            {formattedDateTime(plogging.startTime)} {plogging.place}
          </div>
        </div>
        <div className={style.second_area}>
          <div className={style.dist}>
            거리 <span className={style.largeNumber}>{plogging.distance}</span>
            km
          </div>
          <div className={style.time}>
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
