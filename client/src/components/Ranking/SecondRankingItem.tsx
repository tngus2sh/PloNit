import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";
import { RankInterface, RankDetailInterface } from "interface/rankInterface";

function roundToTwoDecimalPlaces(num: any) {
  return parseFloat(num.toFixed(2));
}
function truncateString(str: string | undefined) {
  if (str && str.length > 6) {
    return <span className={style.truncated}>{str}</span>;
  }
  return str;
}

const SecondRankingItem = ({ data }: { data: RankDetailInterface }) => {
  return (
    <div className={style.second_ranker}>
      <div className={style.rank}>{data.ranking}</div>
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

export default SecondRankingItem;
