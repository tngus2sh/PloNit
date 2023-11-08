import React from "react";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewMemberListPage.module.css";

const MemberItem = () => {
  return (
    <div className={style.Member_Item}>
      <img src="/metamong.png" alt="Crew Image" />
      <div className={style.nickname}>메타몽</div>
      <Icon
        icon="bi:x"
        style={{
          width: "3rem",
          height: "3rem",
        }}
        className={style.x_Icon}
      />
    </div>
  );
};

export default MemberItem;
