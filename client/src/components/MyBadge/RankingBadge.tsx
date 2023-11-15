import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import style from "styles/css/MyBadgePage/MissionBadge.module.css";
import { BadgeInterface } from "interface/badgeInterface";
import { getRankingBadge } from "api/lib/members";

const RankingBadge = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isRankBadge, setRankBadge] = useState<BadgeInterface[]>([]);

  useEffect(() => {
    getRankingBadge(
      accessToken,
      (res) => {
        console.log("랭킹 뱃지 조회 성공");
        console.log(res.data);
        setRankBadge(res.data.resultBody);
      },
      (err) => {
        console.log("랭킹 뱃지 조회 실패", err);
      },
    );
  }, []);
  return (
    <div className={style.missionBadge}>
      {isRankBadge ? (
        <>
          {isRankBadge.map((badge, index) => (
            <div className={style.imageContainer} key={index}>
              <img
                src={badge.isMine ? badge.image : "/non_badge.png"}
                alt="뱃지"
              />
            </div>
          ))}
        </>
      ) : (
        <div>랭킹 뱃지가 존재하지 않습니다.</div>
      )}
    </div>
  );
};

export default RankingBadge;
