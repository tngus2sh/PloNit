import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";
import { RankInterface, RankDetailInterface } from "interface/rankInterface";
// { rankingList }: { crew: RankInterface }
const SecondRankingItem = ({ data }: { data: RankDetailInterface }) => {
  return (
    <div className={style.first_ranker}>
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

export default SecondRankingItem;
