import React from "react";
import { useNavigate } from "react-router-dom";
import style from "styles/css/ProfilePage.module.css";
import { UserInterface } from "interface/authInterface";

const UserInfo = ({ user }: { user: UserInterface }) => {
  const navigate = useNavigate();
  console.log(user);

  const goProfileEdit = () => {
    navigate("/profile/edit");
  };

  return (
    <div className={style.user_info}>
      <div className={style.my_name}>
        <div className={style.left_area}>
          <img src={user.profileImg} alt="프로필" />
          <div className={style.text}>
            <div>
              <span className={style.large}>{user.nickname}</span> 님
            </div>
            <div className={style.email}>{user.email}</div>
          </div>
        </div>
        <div className={style.right_area} onClick={goProfileEdit}>
          프로필 수정
        </div>
      </div>
      <div className={style.my_situation}>
        <div className={style.my_content}>
          <div className={style.title}>참여한 플로깅</div>
          <div className={style.count}>{user.ploggingCount}</div>
        </div>
        <div className={style.my_content}>
          <div className={style.title}>나의 크루</div>
          <div className={style.count}>{user.crewCount}</div>
        </div>
        <div className={style.my_content}>
          <div className={style.title}>수집한 뱃지</div>
          <div className={style.count}>{user.badgeCount}</div>
        </div>
      </div>
    </div>
  );
};

export default UserInfo;
