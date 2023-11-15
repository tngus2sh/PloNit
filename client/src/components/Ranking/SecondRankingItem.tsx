import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";
import { RankInterface } from "interface/rankInterface";

const SecondRankingItem = ({ data }: { data: RankInterface }) => {
  return (
    <div className={style.second_ranker}>
      <div className={style.rank}>2</div>
      <img className={style.user} src="/metamong.png" alt="몽" />

      <div className={style.nickname}>메타몽</div>
      <div className={style.dist}>
        <span className={style.large}>27.45</span>km
      </div>
    </div>
  );
};

export default SecondRankingItem;
