import React from "react";
import { useNavigate } from "react-router-dom";
import style from "styles/css/ProfilePage.module.css";

const UserInfo = () => {
  const navigate = useNavigate();

  const goProfileEdit = () => {
    navigate("/profile/edit");
  };

  return (
    <div className={style.user_info}>
      <div className={style.my_name}>
        <div className={style.left_area}>
          <img src="/metamong.png" alt="몽" />
          <div className={style.text}>
            <div>
              <span className={style.large}>메타몽</span> 님
            </div>
            <div className={style.email}>abcd1234@naver.com</div>
          </div>
        </div>
        <div className={style.right_area} onClick={goProfileEdit}>
          프로필 수정
        </div>
      </div>
      <div className={style.my_situation}>
        <div className={style.my_content}>
          <div className={style.title}>참여한 플로깅</div>
          <div className={style.count}>5</div>
        </div>
        <div className={style.my_content}>
          <div className={style.title}>나의 크루</div>
          <div className={style.count}>2</div>
        </div>
        <div className={style.my_content}>
          <div className={style.title}>수집한 뱃지</div>
          <div className={style.count}>3</div>
        </div>
      </div>
    </div>
  );
};

export default UserInfo;
