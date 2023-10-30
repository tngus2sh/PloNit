import React from "react";
import style from "styles/css/HomePage/CrewPloggingCard.module.css";
import CommonButton from "components/common/CommonButton";

const CrewPloggingCard = () => {
  return (
    <div className={style.plogging_card}>
      <div className={style.plogging_text}>
        <div className={style.crewping_leader}>내가 대장</div>
        <div className={style.crewping_name}>장덕동 지킴이</div>
        <div className={style.crewping_dday}>D - 3</div>
        <div className={style.crewping_date}>10.27(금) 17:00~18:30</div>
        <div className={style.crewping_place}>수완호수공원</div>
        <div className={style.crewping_member}>크루핑 멤버 5</div>
        <div className={style.member_img}>
          <img src="/metamong.png" alt="몽" />
          <img src="/metamong.png" alt="몽" />
        </div>
      </div>
      <CommonButton
        text="시작하기"
        styles={{
          backgroundColor: "#2cd261",
        }}
      />
    </div>
  );
};

export default CrewPloggingCard;
