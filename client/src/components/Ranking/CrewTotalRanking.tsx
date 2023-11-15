import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
// import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface, RankDetailInterface } from "interface/rankInterface";
import { getCrewTotalRank } from "api/lib/rank";

const CrewTotalRanking = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isCrewTotalRank, setCrewTotalRank] = useState<RankInterface>(
    {} as RankInterface,
  );
  const [isCrewTotalList, setCrewTotalList] = useState<RankDetailInterface[]>(
    [],
  );

  useEffect(() => {
    getCrewTotalRank(
      accessToken,
      (res) => {
        console.log("크루 전체 랭킹 조회 성공");
        console.log(res.data);
        setCrewTotalRank(res.data.resultBody);
        setCrewTotalList(res.data.resultBody.membersRanks);
      },
      (err) => {
        console.log("크루 전체 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log("crewrank: ", isCrewTotalRank);
  console.log("crewlist: ", isCrewTotalRank.membersRanks);
  console.log("crewvalue: ", isCrewTotalRank.membersRanks[0]);
  console.log("crewvalue: ", isCrewTotalList);

  return (
    /*
    <div className={style.ranking}>
      <div className={style.top}>
        <SecondRankingItem data={isCrewTotalList[1]} />
        <FirstRankingItem data={isCrewTotalList[0]} />
        <SecondRankingItem data={isCrewTotalList[2]} />
      </div>
      {[3, 4, 5].map((index) => (
        <BasicRankingItem key={index} data={isCrewTotalList[index]} />
      ))}
    </div>
    */
    <div></div>
  );
};

export default CrewTotalRanking;
