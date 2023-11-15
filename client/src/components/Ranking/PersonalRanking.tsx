import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface, RankDetailInterface } from "interface/rankInterface";
import { getMemberRank } from "api/lib/rank";

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
        setMemberRank(res.data.resultBody); // API 응답으로 rankData 업데이트
        setMemberList(res.data.resultBody.membersRanks);
      },
      (err) => {
        console.log("개인 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isMemberRank);
  console.log(isMemberRank.membersRanks);
  console.log(isMemberRank.membersRanks[0]);
  return (
    <div className={style.ranking}>
      <div className={style.top}>
        <SecondRankingItem data={isMemberList[1]} />
        <FirstRankingItem data={isMemberList[0]} />
        <SecondRankingItem data={isMemberList[2]} />
      </div>
      {[3, 4, 5].map((index) => (
        <BasicRankingItem key={index} data={isMemberList[index]} />
      ))}
    </div>
  );
};

export default PersonalRanking;
