import React, { useState, useEffect } from "react";
import style from "styles/css/MyRankPage/MyRankMain.module.css";

const MyRankMain = () => {
  return (
    <div className={style.ranking_info_container}>
      <div className={style.rank_container}>
        <div className={style.rank_title}>순위</div>
        <div className={style.rank_data}>27위</div>
      </div>

      <div className={style.profile_container}>
        {/* <img src="/metamong.png" alt="몽" /> */}
        <img src="/test.png" />
        <div>메타몽</div>
      </div>

      <div className={style.distance_container}>
        <div className={style.distance_title}>누적 거리</div>
        <div>
          <span className={style.distance_data}>10.27</span>km
        </div>
      </div>
    </div>
  );
};

export default MyRankMain;
