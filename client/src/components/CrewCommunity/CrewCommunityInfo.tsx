import React from "react";
import { useNavigate } from "react-router-dom";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCommunityPage/CrewCommunityInfo.module.css";

const CrewCommunityInfo = () => {
  const navigate = useNavigate();
  const handleGoCommunityDetail = () => {
    navigate("/crew/community/detail");
  };
  const handleGoMemberList = () => {
    navigate("/crew/member");
  };
  const crewImageStyle = {
    backgroundImage: `url("/crewbg.png")`,
  };
  return (
    <div className={style.crew_info}>
      <div className={style.crew_img} style={crewImageStyle}></div>
      <div className={style.crew_text}>
        <div className={style.crew_title}>
          <div className={style.crew_name} onClick={handleGoCommunityDetail}>
            장덕동 플로깅 크루
          </div>
          <Icon
            icon="bi:chevron-right"
            style={{
              width: "1.5rem",
              height: "1.5rem",
            }}
            onClick={handleGoCommunityDetail}
          />
        </div>
        <div className={style.crew_content}>
          <div className={style.crew_member} onClick={handleGoMemberList}>
            멤버 25
          </div>
          <div className={style.invite}>
            초대a
            <Icon icon="bi:paperclip" />
          </div>
        </div>
      </div>
    </div>
  );
};

export default CrewCommunityInfo;
