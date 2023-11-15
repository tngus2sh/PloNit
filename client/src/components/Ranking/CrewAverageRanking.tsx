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
  const [isAvgRank, setAvgRank] = useState<RankInterface>({} as RankInterface);

  // 크루 랭킹 리스트만 가져오는 값
  const [isAvgList, setAvgList] = useState<RankDetailInterface[]>([]);

  useEffect(() => {
    getCrewAVGRank(
      accessToken,
      (res) => {
        console.log("크루 전체 랭킹 조회 성공");
        console.log(res.data);
        setAvgRank(res.data.resultBody);
        setAvgList(res.data.resultBody.membersRanks);
      },
      (err) => {
        console.log("크루 전체 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isAvgRank);
  console.log(isAvgList);
  return (
    <div className={style.ranking}>
      {
        <div className={style.top}>
          {isAvgList[1] && <SecondRankingItem data={isAvgList[1]} />}
          {isAvgList[0] && <FirstRankingItem data={isAvgList[0]} />}
          {isAvgList[2] && <SecondRankingItem data={isAvgList[2]} />}
        </div>
      }
      {[3, 4, 5].map(
        (index) =>
          isAvgList[index] && (
            <BasicRankingItem key={index} data={isAvgList[index]} />
          ),
      )}
    </div>
  );
};

export default CrewAverageRanking;
