import React from "react";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";

const CrewTotalRanking = () => {
  return (
    <div className={style.ranking}>
      <div className={style.top}>
        <SecondRankingItem />
        <FirstRankingItem />
        <SecondRankingItem />
      </div>
      <BasicRankingItem />
      <BasicRankingItem />
      <BasicRankingItem />
    </div>
  );
};

export default CrewTotalRanking;
