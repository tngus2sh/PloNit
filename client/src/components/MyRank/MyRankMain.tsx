import React from "react";
import style from "styles/css/MyRankPage/MyRankMain.module.css";

const MyRankMain = () => {
  return (
    <div className={style.mymain}>
      <div className={style.left}>
        <div className={style.title}>순위</div>
        <div>
          <span className={style.large}>27</span>위
        </div>
      </div>
      <div className={style.middle}>
        <img src="/metamong.png" alt="몽" />
        <div>메타몽</div>
      </div>
      <div className={style.right}>
        <div className={style.title}>누적 거리</div>
        <div>
          <span className={style.large}>10.27</span>km
        </div>
      </div>
    </div>
  );
};

export default MyRankMain;
