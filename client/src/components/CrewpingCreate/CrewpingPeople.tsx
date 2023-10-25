import React from "react";
import style from "styles/css/CrewpingCreatePage.module.css";

const CrewpingPeople = () => {
  return (
    <div className={style.people_area}>
      <label className={style.label} htmlFor="person">
        모집 인원
      </label>
      <div className={style.people_content}>
        <input
          type="number"
          name="people"
          id="people"
          className={style.inputBox}
        />
        <div className={style.slash}>/</div>
        <div className={style.limit}>10</div>
      </div>
    </div>
  );
};

export default CrewpingPeople;
