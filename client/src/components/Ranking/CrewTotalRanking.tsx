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
  const [isTotalRank, setTotalRank] = useState<RankInterface>(
    {} as RankInterface,
  );
  const [isTotalList, setTotalList] = useState<RankDetailInterface[]>([]);

  useEffect(() => {
    getCrewTotalRank(
      accessToken,
      (res) => {
        console.log("크루 전체 랭킹 조회 성공");
        console.log(res.data);
        setTotalRank(res.data.resultBody);
        setTotalList(res.data.resultBody.membersRanks);
      },
      (err) => {
        console.log("크루 전체 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isTotalRank);
  console.log(isTotalList);

  return (
    <div className={style.ranking}>
      {
        <div className={style.top}>
          {isTotalList[1] && <SecondRankingItem data={isTotalList[1]} />}
          {isTotalList[0] && <FirstRankingItem data={isTotalList[0]} />}
          {isTotalList[2] && <SecondRankingItem data={isTotalList[2]} />}
        </div>
      }
      {isTotalList.map((data, index) => {
        if (index >= 3) {
          return <BasicRankingItem key={index} data={data} />;
        }
        return null;
      })}
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewTotalRanking;
