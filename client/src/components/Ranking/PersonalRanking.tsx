import React from "react";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";

const PersonalRanking = () => {
  return (
    <div className={style.ranking}>
      <div className={style.top}>
        <SecondRankingItem />
        <FirstRankingItem />
        <SecondRankingItem />
      </div>
      <BasicRankingItem />
    </div>
  );
};

export default PersonalRanking;
