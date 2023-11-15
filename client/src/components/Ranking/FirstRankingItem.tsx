import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";
import { RankDetailInterface } from "interface/rankInterface";

const FirstRankingItem = ({ data }: { data: RankDetailInterface }) => {
  return (
    <div className={style.first_ranker}>
      <img className={style.crown} src="/crown.png" alt="왕관" />
      <img
        className={style.user}
        src={data?.crewImage || data?.profileImage}
        alt="몽"
      />

      <div className={style.nickname}>{data.nickname}</div>
      <div className={style.dist}>
        <span className={style.large}>{data.distance}</span>km
      </div>
    </div>
  );
};

export default FirstRankingItem;
