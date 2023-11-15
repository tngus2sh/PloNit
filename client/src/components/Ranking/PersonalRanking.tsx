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

  useEffect(() => {
    getMemberRank(
      accessToken,
      (res) => {
        console.log("개인 랭킹 조회 성공");
        console.log(res.data);
        setMemberRank(res.data.resultBody); // API 응답으로 rankData 업데이트
      },
      (err) => {
        console.log("개인 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isMemberRank);
  console.log(isMemberRank.rankingList);
  console.log(isMemberRank.rankingList[0]);
  console.log(isMemberRank.rankingList.membersRanks);
  console.log(isMemberRank.rankingList.membersRanks[0]);

  return (
    <div className={style.ranking}>
<<<<<<< HEAD
      <div className={style.top}>
        {isMemberRank.rankingList > 1 && (
=======
      {/* <div className={style.top}>
        {isMemberRank.rankingList && isMemberRank.rankingList.length > 1 && (
>>>>>>> 996fd5a654a039da081b416020d1d42b91da8ff0
          <SecondRankingItem data={isMemberRank} />
        )}
        {isMemberRank.rankingList > 0 && (
          <FirstRankingItem data={isMemberRank} />
        )}
        {isMemberRank.rankingList > 2 && (
          <SecondRankingItem data={isMemberRank} />
        )}
      </div>
      <BasicRankingItem /> */}
    </div>
  );
};

export default PersonalRanking;
