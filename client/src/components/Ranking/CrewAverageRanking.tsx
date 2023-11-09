import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface } from "interface/rankInterface";
import { getCrewAVGRank } from "api/lib/rank";

const CrewAverageRanking = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [isCrewAvgRank, setCrewAvgRank] = useState<RankInterface>(
    {} as RankInterface,
  );

  useEffect(() => {
    getCrewAVGRank(
      accessToken,
      (res) => {
        console.log("크루 전체 랭킹 조회 성공");
        console.log(res.data);
        setCrewAvgRank(res.data.resultBody);
      },
      (err) => {
        console.log("크루 전체 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isCrewAvgRank);
  return (
    <div className={style.ranking}>
      <div className={style.top}>
        <SecondRankingItem />
        <FirstRankingItem />
        <SecondRankingItem />
      </div>
      <BasicRankingItem />
      <BasicRankingItem />
    </div>
  );
};

export default CrewAverageRanking;
