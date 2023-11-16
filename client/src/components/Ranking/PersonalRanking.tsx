import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface, RankDetailInterface } from "interface/rankInterface";
import { getMemberRank } from "api/lib/rank";

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
    <div className={style.ranking_container}>
      <div className={style.ranking_top_container}>
        <div className={style.season_container}>
          <div className={style.season_info}>
            {isMemberRank.startDate && formattedSeason(isMemberRank.startDate)}
          </div>
          <div className={style.season_detail}>
            {`(`}
            {isMemberRank.startDate &&
              formattedDate(isMemberRank.startDate)} ~{" "}
            {isMemberRank.endDate && formattedDate(isMemberRank.endDate)}
            {`)`}
          </div>
        </div>
        <div className={style.member_container}>
          {isMemberList[1] && <SecondRankingItem data={isMemberList[1]} />}
          {isMemberList[0] && <FirstRankingItem data={isMemberList[0]} />}
          {isMemberList[2] && <SecondRankingItem data={isMemberList[2]} />}
        </div>
      </div>

      <div className={style.ranking_bottom_container}>
        {isMemberList.map((data, index) => {
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

export default PersonalRanking;
