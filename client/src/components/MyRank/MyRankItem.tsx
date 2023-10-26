import React from "react";
import style from "styles/css/MyRankPage/MyRankItem.module.css";

const MyRankItem = () => {
  return (
    <div className={style.rank_item}>
      <div className={style.myrank}>
        <span className={style.large}>245</span>위
      </div>
      <div className={style.rank_detail}>
        <div className={style.season}>10월 1일~10월 15일(10-1시즌)</div>
        <div className={style.dist}>
          <span className={style.large}>8.54</span>km
        </div>
      </div>
    </div>
  );
};

export default MyRankItem;
