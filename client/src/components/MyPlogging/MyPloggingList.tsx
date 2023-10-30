import React, { useState } from "react";
import MyPloggingItem from "./MyPloggingItem";
import style from "styles/css/MyPloggingPage/MyPloggingList.module.css";

const MyPloggingList = () => {
  const [selectBox1Value, setSelectBox1Value] = useState("");
  return (
    <div className={style.myplogging_list}>
      <div className={style.top_section}>
        <div className={style.date}>10월 15일의 기록</div>
        <div>
          <select
            value={selectBox1Value}
            onChange={(e) => setSelectBox1Value(e.target.value)}
          >
            <option value="">전체</option>
            <option value="private">개인</option>
            <option value="volunteer">봉사</option>
            <option value="crew">크루</option>
          </select>
        </div>
      </div>
      <MyPloggingItem />
    </div>
  );
};

export default MyPloggingList;
