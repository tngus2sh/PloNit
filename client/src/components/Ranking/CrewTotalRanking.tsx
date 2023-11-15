import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface } from "interface/rankInterface";
import { getCrewTotalRank } from "api/lib/rank";

const CrewTotalRanking = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isCrewTotalRank, setCrewTotalRank] = useState<RankInterface>(
    {} as RankInterface,
  );

  useEffect(() => {
    getCrewTotalRank(
      accessToken,
      (res) => {
        console.log("크루 전체 랭킹 조회 성공");
        console.log(res.data);
        setCrewTotalRank(res.data.resultBody);
      },
      (err) => {
        console.log("크루 전체 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isCrewTotalRank);

  return (
    <div className={style.ranking}>
      <div className={style.top}>
        {isCrewTotalRank.rankingList &&
          isCrewTotalRank.rankingList.length > 1 && (
            <SecondRankingItem data={isCrewTotalRank} />
          )}
        {isCrewTotalRank.rankingList &&
          isCrewTotalRank.rankingList.length > 0 && (
            <FirstRankingItem data={isCrewTotalRank} />
          )}
        {isCrewTotalRank.rankingList &&
          isCrewTotalRank.rankingList.length > 2 && (
            <SecondRankingItem data={isCrewTotalRank} />
          )}
      </div>
      <BasicRankingItem />
      <BasicRankingItem />
      <BasicRankingItem />
    </div>
  );
};

export default CrewTotalRanking;
