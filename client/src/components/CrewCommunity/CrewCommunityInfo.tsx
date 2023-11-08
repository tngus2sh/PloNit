import React from "react";
import { useNavigate } from "react-router-dom";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCommunityPage/CrewCommunityInfo.module.css";
import { CrewInterface } from "interface/crewInterface";

const CrewCommunityInfo = ({ crew }: { crew: CrewInterface }) => {
  const navigate = useNavigate();
  const handleGoCommunityDetail = () => {
    navigate(`/crew/community/detail/${crew.id}`);
  };
  const handleGoMemberList = () => {
    navigate(`/crew/member/${crew.id}`);
  };
  const crewImageStyle = {
    backgroundImage: `url("${crew.crewImage}")`,
  };
  return (
    <div className={style.crew_info}>
      <div className={style.crew_img} style={crewImageStyle}></div>
      <div className={style.crew_text}>
        <div className={style.crew_title}>
          <div className={style.crew_name} onClick={handleGoCommunityDetail}>
            {crew.name}
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
            멤버 {crew.cntPeople}
          </div>
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
