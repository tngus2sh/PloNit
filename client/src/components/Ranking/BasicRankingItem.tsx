import React from "react";
import style from "styles/css/RankingPage/RankingItem.module.css";
import { RankDetailInterface } from "interface/rankInterface";

function roundToTwoDecimalPlaces(num: any) {
  return parseFloat(num.toFixed(2));
}

const BasicRankingItem = ({ data }: { data: RankDetailInterface }) => {
  return (
    <div className={style.basic_item}>
      <div className={style.rank}>{data.ranking}</div>
      <div className={style.user}>
        <img src={data.crewImage || data.profileImage} alt="몽" />
        <div className={style.username}>{data.nickName}</div>
      </div>
      <div className={style.dist}>
        <span className={style.large}>
          {roundToTwoDecimalPlaces(data.distance)}
        </span>
        km
      </div>
    </div>
  );
};

export default BasicRankingItem;
