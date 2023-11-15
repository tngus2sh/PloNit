import React from "react";
import { useNavigate } from "react-router-dom";
import style from "styles/css/CrewList/CrewItem.module.css";
import { CrewInterface } from "interface/crewInterface";
import FmdGoodRoundedIcon from "@mui/icons-material/FmdGoodRounded";
import FaceRoundedIcon from "@mui/icons-material/FaceRounded";

const CrewItem = ({ crew }: { crew: CrewInterface }) => {
  const navigate = useNavigate();
  const goCommunity = () => {
    navigate(`/crew/community/${crew.id}`);
  };

  return (
    <div className={style.crew_Item} onClick={goCommunity}>
      <div className={style.crew_image_container}>
        <img src={crew.crewImage} alt="Crew Image" />
      </div>
      <div className={style.crew_info_container}>
        <div className={style.crew_content}>
          <div className={style.title_container}>
            <div className={style.crew_title}>
              <div>{crew.name}</div>
            </div>
          </div>

          <div className={style.people_container}>
            <div className={style.people}>
              <FaceRoundedIcon sx={{ fontSize: "1.2rem" }} />
              &nbsp;멤버 {crew.cntPeople}
            </div>
          </div>

          <div className={style.place_container}>
            <div className={style.place}>
              <FmdGoodRoundedIcon sx={{ fontSize: "1.2rem" }} />
              &nbsp;{crew.region}
            </div>
          </div>

          <div className={style.first}></div>
        </div>
      </div>
    </div>
  );
};

export default CrewItem;
