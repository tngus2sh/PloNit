import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import style from "styles/css/MyRankPage/MyRankMain.module.css";
import { MyRankInterface } from "interface/rankInterface";

// function roundToTwoDecimalPlaces(num: any) {
//   return parseFloat(num.toFixed(2));
// }

const MyRankMain = ({ rank }: { rank: MyRankInterface }) => {
  const profile = useSelector((state: any) => state.user.info.profileImage);
  const nickname = useSelector((state: any) => state.user.info.nickname);
  return (
    <div className={style.ranking_info_container}>
      <div className={style.rank_container}>
        <div className={style.rank_title}>순위</div>
        <div className={style.rank_data}>{rank.ranking}위</div>
      </div>

      <div className={style.profile_container}>
        <img src={profile} alt="몽" />
        <div>{nickname}</div>
      </div>

      <div className={style.distance_container}>
        <div className={style.distance_title}>누적 거리</div>
        <div>
          <span className={style.distance_data}>{rank.distance}</span>
          km
        </div>
      </div>
    </div>
  );
};

export default MyRankMain;
