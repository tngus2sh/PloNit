import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface, RankDetailInterface } from "interface/rankInterface";
import { getCrewTotalRank } from "api/lib/rank";

const CrewTotalRanking = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isCrewTotalRank, setCrewTotalRank] = useState<RankInterface>(
    {} as RankInterface,
  );
  const [isCrewTotalRankList, setCrewTotalRankList] = useState<
    RankDetailInterface[]
  >([]);

  useEffect(() => {
    getCrewTotalRank(
      accessToken,
      (res) => {
        console.log("크루 전체 랭킹 조회 성공");
        console.log(res.data);
        setCrewTotalRank(res.data.resultBody);
        setCrewTotalRankList(res.data.resultBody.membersRanks);
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
        <SecondRankingItem data={isCrewTotalRankList[1]} />
        <FirstRankingItem data={isCrewTotalRankList[0]} />
        <SecondRankingItem data={isCrewTotalRankList[2]} />
      </div>
      {isCrewTotalRankList.slice(3).map((item, index) => (
        <BasicRankingItem key={index} data={item} />
      ))}
    </div>
  );
};

export default CrewTotalRanking;
