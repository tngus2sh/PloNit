import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import BasicRankingItem from "./BasicRankingItem";
import FirstRankingItem from "./FirstRankingItem";
import SecondRankingItem from "./SecondRankingItem";
import style from "styles/css/RankingPage/RankingList.module.css";
import { RankInterface, MemberRankInterface } from "interface/rankInterface";
import { getMemberRank } from "api/lib/rank";

const PersonalRanking = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [isMemberRank, setMemberRank] = useState<RankInterface>(
    {} as RankInterface,
  );

  useEffect(() => {
    getMemberRank(
      accessToken,
      (res) => {
        console.log("개인 랭킹 조회 성공");
        console.log(res.data);
        setMemberRank(res.data.resultBody);
      },
      (err) => {
        console.log("개인 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isMemberRank);

  return (
    <div className={style.ranking}>
      <div className={style.top}>
        <SecondRankingItem />
        <FirstRankingItem />
        <SecondRankingItem />
      </div>
      <BasicRankingItem />
    </div>
  );
};

export default PersonalRanking;
