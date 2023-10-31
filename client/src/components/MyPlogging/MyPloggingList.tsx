import React, { useState } from "react";
import MyPloggingItem from "./MyPloggingItem";
import style from "styles/css/MyPloggingPage/MyPloggingList.module.css";

interface MyPloggingListProps {
  dateRange: [Date, Date];
}

const MyPloggingList = ({ dateRange }: MyPloggingListProps) => {
  const startDateString = `${
    dateRange[0].getMonth() + 1
  }월 ${dateRange[0].getDate()}일`;
  const endDateString = `${
    dateRange[1].getMonth() + 1
  }월 ${dateRange[1].getDate()}일`;
  console.log(dateRange);
  const [selectBox1Value, setSelectBox1Value] = useState("");
  return (
    <div className={style.myplogging_list}>
      <div className={style.top_section}>
        <div className={style.date}>
          {startDateString !== endDateString && (
            <div className={style.startdate}>{startDateString}에서</div>
          )}
          <div className={style.enddate}>{endDateString}의 기록</div>
        </div>
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
