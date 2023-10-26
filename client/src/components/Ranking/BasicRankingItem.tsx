import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";

const BasicRankingItem = () => {
  return (
    <div className={style.basic_item}>
      <div className={style.rank}>4</div>
      <div className={style.user}>
        <img src="/metamong.png" alt="몽" />
        <div className={style.username}>메타몽</div>
      </div>
      <div className={style.dist}>
        <span className={style.large}>23.84</span>km
      </div>
    </div>
  );
};

export default BasicRankingItem;
