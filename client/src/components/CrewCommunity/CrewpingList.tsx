import React, { useState } from "react";
import CrewpingItem from "./CrewpingItem";
import style from "styles/css/CrewCommunityPage/CrewpingList.module.css";

const CrewpingList = () => {
  const [selectBox1Value, setSelectBox1Value] = useState("");
  return (
    <div>
      <div className={style.top_area}>
        <div>총 5개</div>
        <div>
          <select
            value={selectBox1Value}
            onChange={(e) => setSelectBox1Value(e.target.value)}
          >
            <option value="">선택</option>
            <option value="create">생성순</option>
            <option value="last">마감순</option>
          </select>
        </div>
      </div>
      <CrewpingItem />
    </div>
  );
};

export default CrewpingList;
