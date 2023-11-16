import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";
import { RankDetailInterface } from "interface/rankInterface";

function roundToTwoDecimalPlaces(num: any) {
  return parseFloat(num.toFixed(2));
}
function truncateString(str: string | undefined) {
  if (str && str.length > 7) {
    return str.substring(0, 7) + "...";
  }
  return str;
}
const FirstRankingItem = ({ data }: { data: RankDetailInterface }) => {
  return (
    <div className={style.first_ranker}>
      <img className={style.crown} src="/crown.png" alt="왕관" />
      <img
        className={style.user}
        src={data?.crewImage || data?.profileImage}
        alt="몽"
      />

      <div className={style.nickname}>{truncateString(data.nickName)}</div>
      <div className={style.dist}>
        <span className={style.large}>
          {roundToTwoDecimalPlaces(data.distance)}
        </span>
        km
      </div>
    </div>
  );
};

export default FirstRankingItem;
