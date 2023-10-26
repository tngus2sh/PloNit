import React from "react";
import BasicRankingItem from "./BasicRankingItem";
import style from "styles/css/RankingPage/PersonalRanking.module.css";

const PersonalRanking = () => {
  return (
    <div className={style.personal_ranking}>
      <BasicRankingItem />
    </div>
  );
};

export default PersonalRanking;
