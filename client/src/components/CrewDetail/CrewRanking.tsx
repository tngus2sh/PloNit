import React from "react";
import style from "styles/css/CrewRanking.module.css";

const CrewRanking = () => {
  return (
    <div className={style.crew_ranking}>
      <div className={style.title}>크루 랭킹</div>
      <div className={style.date}>2023년 10월 2시즌</div>
      <div className={style.ranking}>
        <div className={style.dist}>12.47km</div>
        <div className={style.rank}>255등</div>
      </div>
    </div>
  );
};

export default CrewRanking;
