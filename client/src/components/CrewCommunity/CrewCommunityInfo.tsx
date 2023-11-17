import React from "react";
import { useNavigate } from "react-router-dom";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCommunityPage/CrewCommunityInfo.module.css";
import { CrewInterface } from "interface/crewInterface";

const CrewCommunityInfo = ({
  crew,
  master,
}: {
  crew: CrewInterface;
  master: boolean;
}) => {
  const navigate = useNavigate();
  const handleGoCommunityDetail = () => {
    navigate(`/crew/community/detail/${crew.id}`);
  };
  const handleGoMemberList = () => {
    navigate(`/crew/member/${crew.id}`);
  };
  const handleGoApprovalList = () => {
    navigate(`/crew/community/approval/${crew.id}`);
  };
  const crewImageStyle = {
    backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.4)), url(${crew.crewImage})`,
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
              marginLeft: "0.2rem",
            }}
            onClick={handleGoCommunityDetail}
          />
        </div>
        <div className={style.crew_content}>
          <div className={style.crew_member} onClick={handleGoMemberList}>
            멤버 {crew.cntPeople}
            <Icon
              icon="bi:chevron-right"
              style={{
                width: "1.2rem",
                height: "1.2rem",
                marginLeft: "0.2rem",
              }}
              onClick={handleGoMemberList}
            />
          </div>
          {/* <div className={style.invite}>
            초대
            <Icon icon="bi:paperclip" />
          </div> */}
          {master && (
            <div className={style.allow} onClick={handleGoApprovalList}>
              가입 대기
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CrewCommunityInfo;
