import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { BasicTopBar } from "components/common/TopBar";
import UserInfo from "components/Profile/UserInfo";
import { Icon } from "@iconify/react";
import style from "styles/css/ProfilePage.module.css";
import { UserInterface } from "interface/authInterface";
import { logout } from "api/lib/auth";
import { getProfile } from "api/lib/members";

const ProfilePage = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const User = useSelector((state: any) => state.user);
  console.log(User);
  const [isMyData, setMyData] = useState<UserInterface>({} as UserInterface);

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
  const handleLogout = () => {
    logout(
      accessToken,
      (res) => {
        console.log(res.data);
        console.log("로그아웃 성공");
        navigate("/login");
      },
      (err) => {
        console.log("로그아웃 실패", err);
      },
    );
  };
  useEffect(() => {
    getProfile(
      accessToken,
      (res) => {
        console.log("내 정보 조회 성공");
        console.log(res.data.resultBody);
        setMyData(res.data.resultBody);
      },
      (err) => {
        console.log("내 정보 조회 실패", err);
      },
    );
  }, []);

  return (
    <div>
      <BasicTopBar text="마이페이지" />
      <UserInfo user={isMyData} />
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
        <div className={style.part} onClick={handleLogout}>
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
