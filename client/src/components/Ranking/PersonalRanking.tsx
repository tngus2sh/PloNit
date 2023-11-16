import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface, RankDetailInterface } from "interface/rankInterface";
import { getMemberRank } from "api/lib/rank";

const formattedSeason = (date: any) => {
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).slice(-2);
  const day = date.getDate().slice(-2);
  const season = day === "1" ? 1 : 2;

  return `${year}년 ${month}월 ${season}시즌`;
};

const formattedDate = (date: any) => {
  const dateObj = new Date(date);
  const month = ("0" + (date.getMonth() + 1)).slice(-2);
  const day = ("0" + date.getDate()).slice(-2);
  return `${month}월 ${day}일`;
};
const endformattedDate = (date: any) => {
  const dateObj = new Date(date);
  dateObj.setDate(dateObj.getDate() - 1);
  const month = ("0" + (dateObj.getMonth() + 1)).slice(-2);
  const day = ("0" + dateObj.getDate()).slice(-2);
  return `${month}월 ${day}일`;
};

const PersonalRanking = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isMemberRank, setMemberRank] = useState<RankInterface>(
    {} as RankInterface,
  );
  const [isMemberList, setMemberList] = useState<RankDetailInterface[]>([]);
  useEffect(() => {
    getMemberRank(
      accessToken,
      (res) => {
        console.log("개인 랭킹 조회 성공");
        console.log(res.data);
        setMemberRank(res.data.resultBody);
        setMemberList(res.data.resultBody.membersRanks);
      },
      (err) => {
        console.log("개인 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isMemberRank);
  console.log(isMemberList);
  return (
    <div className={style.ranking}>
      <div className={style.season}>
        <div className={style.detail}>
          {isMemberRank.startDate && formattedSeason(isMemberRank.startDate)}
        </div>
        <div className={style.date}>
          {isMemberRank.startDate && formattedDate(isMemberRank.startDate)} -{" "}
          {isMemberRank.endDate && endformattedDate(isMemberRank.endDate)}
        </div>
      </div>
      {
        <div className={style.top}>
          {isMemberList[1] && <SecondRankingItem data={isMemberList[1]} />}
          {isMemberList[0] && <FirstRankingItem data={isMemberList[0]} />}
          {isMemberList[2] && <SecondRankingItem data={isMemberList[2]} />}
        </div>
      }
      {isMemberList.map((data, index) => {
        if (index >= 3) {
          return <BasicRankingItem key={index} data={data} />;
        }
        return null;
      })}
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default PersonalRanking;
