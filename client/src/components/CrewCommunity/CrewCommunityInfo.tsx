import React from "react";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCommunityInfo.module.css";

const CrewCommunityInfo = () => {
  const crewImageStyle = {
    backgroundImage: `url("/crewbg.png")`,
  };
  return (
    <div className={style.crew_info}>
      <div className={style.crew_img} style={crewImageStyle}></div>
      <div className={style.crew_text}>
        <div className={style.crew_title}>
          장덕동 플로깅 크루
          <Icon
            icon="bi:chevron-right"
            style={{
              width: "1.5rem",
              height: "1.5rem",
            }}
          />
        </div>
        <div className={style.crew_content}>
          <div className={style.crew_member}>멤버 25</div>
          <div className={style.invite}>
            초대
            <Icon icon="bi:paperclip" />
          </div>
        </div>
      </div>
    </div>
  );
};

export default CrewCommunityInfo;
