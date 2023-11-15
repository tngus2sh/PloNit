import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";
<<<<<<< HEAD
import { RankInterface, CrewRankInterface } from "interface/rankInterface";

const FirstRankingItem = ({ data }: { data: RankInterface }) => {
  const firstItem = data.rankingList[0];
  console.log("firstItem: ", { firstItem });
=======
import { RankInterface, RankDetailInterface } from "interface/rankInterface";

const FirstRankingItem = ({ data }: { data: RankDetailInterface }) => {
>>>>>>> 996fd5a654a039da081b416020d1d42b91da8ff0
  return (
    <div className={style.first_ranker}>
      <img className={style.crown} src="/crown.png" alt="왕관" />
      <img
        className={style.user}
        src={data.crewImage || data.profileImage}
        alt="몽"
      />

      <div className={style.nickname}>{data.nickname}</div>
      <div className={style.dist}>
        <span className={style.large}>{data.distance}</span>km
      </div>
    </div>
  );
};

export default FirstRankingItem;
