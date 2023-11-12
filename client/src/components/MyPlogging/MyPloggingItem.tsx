import React from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import style from "styles/css/MyPloggingPage/MyPloggingItem.module.css";
import { PloggingLog } from "interface/ploggingInterface";

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
            {plogging.start_time} {plogging.place}
          </div>
        </div>
        <div className={style.second_area}>
          <div className={style.dist}>
            거리 <span className={style.largeNumber}>{plogging.distance}</span>
            km
          </div>
          <div className={style.time}>
            시간
            <span className={style.largeNumber}>{plogging.total_time}</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyPloggingItem;
