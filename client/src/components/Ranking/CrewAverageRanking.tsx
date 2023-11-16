import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface, RankDetailInterface } from "interface/rankInterface";
import { getCrewAVGRank } from "api/lib/rank";

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
const endformattedDate = (datestr: any) => {
  const dateObj = new Date(datestr);
  dateObj.setDate(dateObj.getDate() - 1);
  const month = dateObj.getMonth() + 1;
  const day = dateObj.getDate();
  return `${month}월 ${day}일`;
};

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
        setAvgList(res.data.resultBody.crewsAvgRanks);
      },
      (err) => {
        console.log("크루 전체 랭킹 조회 실패", err);
      },
    );
  }, []);

  return (
    <div className={style.ranking}>
      <div className={style.season}>
        <div className={style.detail}>
          {isAvgRank.startDate && formattedSeason(isAvgRank.startDate)}
        </div>
        <div className={style.date}>
          {isAvgRank.startDate && formattedDate(isAvgRank.startDate)} -{" "}
          {isAvgRank.endDate && endformattedDate(isAvgRank.endDate)}
        </div>
      </div>
      {
        <div className={style.top}>
          {isAvgList[1] && <SecondRankingItem data={isAvgList[1]} />}
          {isAvgList[0] && <FirstRankingItem data={isAvgList[0]} />}
          {isAvgList[2] && <SecondRankingItem data={isAvgList[2]} />}
        </div>
      }
      {isAvgList.map((data, index) => {
        if (index >= 3) {
          return <BasicRankingItem key={index} data={data} />;
        }
        return null;
      })}
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewAverageRanking;
