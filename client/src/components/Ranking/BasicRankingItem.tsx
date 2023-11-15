import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";
import { RankDetailInterface } from "interface/rankInterface";

const BasicRankingItem = ({ data }: { data: RankDetailInterface }) => {
  return (
    <div className={style.basic_item}>
      <div className={style.rank}>4</div>
      <div className={style.user}>
        <img src={data.crewImage || data.profileImage} alt="몽" />
        <div className={style.username}>{data.nickname}</div>
      </div>
      <div className={style.dist}>
        <span className={style.large}>{data.distance}</span>km
      </div>
    </div>
  );
};

export default BasicRankingItem;
