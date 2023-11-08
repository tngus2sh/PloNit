import React from "react";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewMemberListPage.module.css";
import { MemberInterface } from "interface/crewInterface";

const MemberItem = ({
  member,
  master,
}: {
  member: MemberInterface;
  master: boolean;
}) => {
  return (
    <div className={style.Member_Item}>
      <img src={member.profileImage} alt="Crew Image" />
      <div className={style.nickname}>{member.nickname}</div>
      {master && (
        <Icon
          icon="bi:x"
          style={{
            width: "3rem",
            height: "3rem",
          }}
          className={style.x_Icon}
        />
      )}
    </div>
  );
};

export default MemberItem;
