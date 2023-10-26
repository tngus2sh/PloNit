import React from "react";
import { useNavigate } from "react-router-dom";
import style from "styles/css/MyPloggingPage/MyPloggingItem.module.css";

const MyPloggingItem = () => {
  const navigate = useNavigate();

  const goPloggingDetailHandler = () => {
    navigate("/profile/plogging/detail");
  };
  return (
    <div className={style.myplogging_item} onClick={goPloggingDetailHandler}>
      <div className={style.left_area}>
        <img src="/metamong.png" alt="몽" />
      </div>
      <div className={style.right_area}>
        <div className={style.first_area}>
          <div className={style.divide}>크루</div>
          <div className={style.date_place}>2023.10.15 14:55 수완동, 광주</div>
        </div>
        <div className={style.second_area}>
          <div className={style.dist}>
            거리 <span className={style.largeNumber}>2.12</span>km
          </div>
          <div className={style.time}>
            시간 <span className={style.largeNumber}>42:35</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyPloggingItem;
