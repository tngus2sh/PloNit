import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";

const FirstRankingItem = () => {
  return (
    <div className={style.first_ranker}>
      <img className={style.crown} src="/crown.png" alt="왕관" />
      <img className={style.user} src="/metamong.png" alt="몽" />

      <div className={style.nickname}>메타몽</div>
      <div className={style.dist}>
        <span className={style.large}>27.45</span>km
      </div>
    </div>
  );
};

export default FirstRankingItem;
