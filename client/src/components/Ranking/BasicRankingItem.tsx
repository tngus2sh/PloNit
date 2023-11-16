import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";
import { RankDetailInterface } from "interface/rankInterface";

const BasicRankingItem = ({ data }: { data: RankDetailInterface }) => {
  return (
    <div className={style.rank_member_container}>
      <div className={style.rank_container}>
        <div>{data.ranking}</div>
      </div>
      <div className={style.profile_container}>
        <img src={data.crewImage || data.profileImage} alt="몽" />
        <div>{data.nickName}</div>
      </div>
      <div className={style.distance_container}>
        <span className={style.large}>{data.distance}</span>km
      </div>
    </div>
  );
};

export default BasicRankingItem;
