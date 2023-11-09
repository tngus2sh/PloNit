import React from "react";
import { useNavigate } from "react-router-dom";
import style from "styles/css/CrewList/CrewItem.module.css";
import { CrewInterface } from "interface/crewInterface";

const CrewItem = ({ crew }: { crew: CrewInterface }) => {
  const navigate = useNavigate();
  const goCommunity = () => {
    navigate(`/crew/community/${crew.id}`);
  };

  return (
    <div className={style.crew_Item} onClick={goCommunity}>
      <img src={crew.crewImage} alt="Crew Image" />
      <div className={style.crew_content}>
        <div className={style.crew_title}>{crew.name}</div>
        <div className={style.first}>
          <div className={style.place}>{crew.region}</div>
          <div className={style.people}>멤버 {crew.cntPeople}</div>
        </div>
      </div>
    </div>
  );
};

export default CrewItem;
