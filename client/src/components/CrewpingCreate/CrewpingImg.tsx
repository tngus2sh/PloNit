import React from "react";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewpingCreatePage.module.css";

const CrewpingImg = () => {
  return (
    <div className={style.crewping_img}>
      <img src="/metamong.png" alt="몽" />
      <div className={style.img_edit_icon}>
        <Icon
          icon="bi:pencil"
          className={style.icon}
          style={{ width: "1.5rem", height: "1.5rem" }}
        />
      </div>
    </div>
  );
};

export default CrewpingImg;
