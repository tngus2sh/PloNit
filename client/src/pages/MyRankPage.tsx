import React, { useState } from "react";
import { BackTopBar } from "components/common/TopBar";
import MyRankMain from "components/MyRank/MyRankMain";
import MyRankItem from "components/MyRank/MyRankItem";
import style from "styles/css/MyRankPage.module.css";

const MyRankPage = () => {
  const [selectBox1Value, setSelectBox1Value] = useState("");
  return (
    <div>
      <BackTopBar text="나의 랭킹" />
      <div className={style.myrank}>
        <div className={style.season_date}>10월 16일 ~ 10월 31일</div>
        <div className={style.season}>(10-2 시즌)</div>
        <MyRankMain />
        <div className={style.ranking_list}>
          <div className={style.title}>지난 랭킹</div>
          <div>
            <select
              value={selectBox1Value}
              onChange={(e) => setSelectBox1Value(e.target.value)}
            >
              <option value="">선택</option>
              <option value="graph">그래프</option>
              <option value="list">목록</option>
            </select>
          </div>
        </div>
        <MyRankItem />
        <MyRankItem />
      </div>
    </div>
  );
};

export default MyRankPage;
