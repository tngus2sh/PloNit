import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import MyPloggingItem from "./MyPloggingItem";
import style from "styles/css/MyPloggingPage/MyPloggingList.module.css";
import { PloggingLog } from "interface/ploggingInterface";

interface MyPloggingListProps {
  dateRange: [Date, Date];
  PloggingList: PloggingLog[];
  setType: any;
}

const MyPloggingList = ({
  dateRange,
  PloggingList,
  setType,
}: MyPloggingListProps) => {
  const startDateString = `${
    dateRange[0].getMonth() + 1
  }월 ${dateRange[0].getDate()}일`;
  const endDateString = `${
    dateRange[1].getMonth() + 1
  }월 ${dateRange[1].getDate()}일`;

  const [isSelectedType, setSelectedType] = useState("");
  useEffect(() => {
    setType(isSelectedType);
  }, [isSelectedType]);

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
            value={isSelectedType}
            onChange={(e) => setSelectedType(e.target.value)}
          >
            <option value="">전체</option>
            <option value="IND">개인</option>
            <option value="VOL">봉사</option>
            <option value="CREWPING">크루</option>
          </select>
        </div>
      </div>

      {PloggingList ? (
        <>
          {PloggingList.map((plogging, index) => (
            <MyPloggingItem key={index} plogging={plogging} />
          ))}
        </>
      ) : null}
    </div>
  );
};

export default MyPloggingList;
