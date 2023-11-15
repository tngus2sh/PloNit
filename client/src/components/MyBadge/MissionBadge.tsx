import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import style from "styles/css/MyBadgePage/MissionBadge.module.css";
import { BadgeInterface } from "interface/badgeInterface";
import { getMissionBadge } from "api/lib/members";

const MissionBadge = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isMissionBadge, setMissionBadge] = useState<BadgeInterface[]>([]);

  useEffect(() => {
    getMissionBadge(
      accessToken,
      (res) => {
        console.log("미션 뱃지 조회 성공");
        console.log(res.data);
        setMissionBadge(res.data.resultBody);
      },
      (err) => {
        console.log("미션 뱃지 조회 실패", err);
      },
    );
  }, []);

  return (
    <div className={style.missionBadge}>
      {isMissionBadge ? (
        <>
          {isMissionBadge.map((badge, index) => (
            <div className={style.imageContainer} key={index}>
              <img src={badge.image} alt={badge.name} />
            </div>
          ))}
        </>
      ) : (
        <div>미션 뱃지가 존재하지 않습니다.</div>
      )}
    </div>
  );
};
export default MissionBadge;
