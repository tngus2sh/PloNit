import React from "react";
import { useNavigate } from "react-router-dom";
import style from "styles/css/CrewCommunityPage/CrewpingItem.module.css";
import RoomIcon from "@mui/icons-material/Room";
import PersonIcon from "@mui/icons-material/Person";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";

const CrewpingItem = () => {
  const navigate = useNavigate();

  const goCrewpingDetailHandler = () => {
    navigate("/crew/crewping/detail");
  };

  return (
    <div className={style.crewping_Item} onClick={goCrewpingDetailHandler}>
      <div className={style.crewping_img}>
        <img src="/metamong.png" alt="몽" />
      </div>
      <div className={style.crewping_content}>
        <div className={style.crewping_title}>장덕동 지킴이</div>
        <div className={style.first}>
          <div className={style.place}>
            <RoomIcon
              className={style.people_icon}
              style={{ width: "1.2rem", height: "1.2rem" }}
            />
            <div>수완호수공원</div>
          </div>
          <div className={style.people}>
            <PersonIcon
              className={style.people_icon}
              style={{ width: "1.2rem", height: "1.2rem" }}
            />
            <div>5/8명</div>
          </div>
        </div>
        <div className={style.second}>
          <CalendarMonthIcon style={{ width: "1.2rem", height: "1.2rem" }} />
          <div>날짜</div>
        </div>
      </div>
    </div>
  );
};

export default CrewpingItem;
