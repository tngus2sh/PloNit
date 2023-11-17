import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface, RankDetailInterface } from "interface/rankInterface";
import { getCrewTotalRank } from "api/lib/rank";

const formattedSeason = (datestr: any) => {
  const date = new Date(datestr);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const season = day === 1 ? 1 : 2;

  return `${year}년 ${month}월 ${season}시즌`;
};

const formattedDate = (datestr: any) => {
  const date = new Date(datestr);
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return `${month}월 ${day}일`;
};
// const endformattedDate = (datestr: any) => {
//   const dateObj = new Date(datestr);
//   dateObj.setDate(dateObj.getDate() - 1);
//   const month = dateObj.getMonth() + 1;
//   const day = dateObj.getDate();
//   return `${month}월 ${day}일`;
// };

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
        // console.log("크루 전체 랭킹 조회 성공");
        // console.log(res.data);
        setTotalRank(res.data.resultBody);
        setTotalList(res.data.resultBody.crewsRanks);
      },
      (err) => {
        console.log("크루 전체 랭킹 조회 실패", err);
      },
    );
  }, []);

  return (
    <div className={style.ranking_container}>
      <div className={style.ranking_top_container}>
        <div className={style.season_container}>
          <div className={style.season_info}>
            {isTotalRank.startDate && formattedSeason(isTotalRank.startDate)}
          </div>
          <div className={style.season_detail}>
            {`(`}
            {isTotalRank.startDate &&
              formattedDate(isTotalRank.startDate)} ~{" "}
            {isTotalRank.endDate && formattedDate(isTotalRank.endDate)}
            {`)`}
          </div>
        </div>
        <div className={style.member_container}>
          {isTotalList[1] && <SecondRankingItem data={isTotalList[1]} />}
          {isTotalList[0] && <FirstRankingItem data={isTotalList[0]} />}
          {isTotalList[2] && <SecondRankingItem data={isTotalList[2]} />}
        </div>
      </div>
      <div className={style.ranking_bottom_container}>
        {isTotalList.map((data, index) => {
          if (index >= 3) {
            return <BasicRankingItem key={index} data={data} />;
          }
          return null;
        })}
      </div>

      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewTotalRanking;
