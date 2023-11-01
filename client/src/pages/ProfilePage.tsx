import React from "react";
import { useNavigate } from "react-router-dom";
import { BasicTopBar } from "components/common/TopBar";
import UserInfo from "components/Profile/UserInfo";
import { Icon } from "@iconify/react";
import style from "styles/css/ProfilePage.module.css";

const ProfilePage = () => {
  const navigate = useNavigate();

  const goMyCrew = () => {
    navigate("/profile/crew");
  };

  const goMyPlogging = () => {
    navigate("/profile/plogging");
  };

  const goMyRank = () => {
    navigate("/profile/rank");
  };

  const goMyBadge = () => {
    navigate("/profile/badge");
  };

  return (
    <div>
      <BasicTopBar text="마이페이지" />
      <UserInfo />
      <div className={style.move}>
        <div className={style.part} onClick={goMyCrew}>
          <div>나의 크루</div>
          <Icon
            icon="bi:chevron-right"
            style={{ width: "1.2rem", height: "1.2rem" }}
            className={style.Icon}
          />
        </div>
        <div className={style.part} onClick={goMyPlogging}>
          <div>나의 플로깅</div>
          <Icon
            icon="bi:chevron-right"
            style={{ width: "1.2rem", height: "1.2rem" }}
            className={style.Icon}
          />
        </div>
        <div className={style.part} onClick={goMyRank}>
          <div>나의 랭킹</div>
          <Icon
            icon="bi:chevron-right"
            style={{ width: "1.2rem", height: "1.2rem" }}
            className={style.Icon}
          />
        </div>
        <div className={style.part} onClick={goMyBadge}>
          <div>나의 뱃지</div>
          <Icon
            icon="bi:chevron-right"
            style={{ width: "1.2rem", height: "1.2rem" }}
            className={style.Icon}
          />
        </div>
        <div className={style.part}>
          <div>로그아웃</div>
          <Icon
            icon="bi:chevron-right"
            style={{ width: "1.2rem", height: "1.2rem" }}
            className={style.Icon}
          />
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
