import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface, RankDetailInterface } from "interface/rankInterface";
import { getCrewAVGRank } from "api/lib/rank";

const CrewAverageRanking = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isCrewAvgRank, setCrewAvgRank] = useState<RankInterface>(
    {} as RankInterface,
  );

  // 크루 랭킹 리스트만 가져오는 값
  const [isCrewAvgRankList, setCrewAvgRankList] = useState<
    RankDetailInterface[]
  >([]);

  useEffect(() => {
    getCrewAVGRank(
      accessToken,
      (res) => {
        console.log("크루 전체 랭킹 조회 성공");
        console.log(res.data);
        setCrewAvgRank(res.data.resultBody);
        setCrewAvgRankList(res.data.resultBody.membersRanks);
      },
      (err) => {
        console.log("크루 전체 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isCrewAvgRank);
  return (
    <div className={style.ranking}>
      {
        <div className={style.top}>
          <SecondRankingItem data={isCrewAvgRankList[1]} />
          <FirstRankingItem data={isCrewAvgRankList[0]} />
          <SecondRankingItem data={isCrewAvgRankList[2]} />
        </div>
      }
      {[3, 4, 5].map((index) => (
        <BasicRankingItem key={index} data={isCrewAvgRankList[index]} />
      ))}
    </div>
  );
};

export default CrewAverageRanking;
