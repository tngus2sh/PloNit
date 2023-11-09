import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import MyPloggingItem from "./MyPloggingItem";
import style from "styles/css/MyPloggingPage/MyPloggingList.module.css";
import { searchPloggingUsingDay } from "api/lib/plogging";
import { PloggingLog } from "interface/ploggingInterface";

interface MyPloggingListProps {
  dateRange: [Date, Date];
}

const formattedDate = (date: any) => {
  const year = date.getFullYear();
  const month = ("0" + (date.getMonth() + 1)).slice(-2);
  const day = ("0" + date.getDate()).slice(-2);
  return `${year}-${month}-${day}`;
};

const MyPloggingList = ({ dateRange }: MyPloggingListProps) => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const startDateString = `${
    dateRange[0].getMonth() + 1
  }월 ${dateRange[0].getDate()}일`;
  const endDateString = `${
    dateRange[1].getMonth() + 1
  }월 ${dateRange[1].getDate()}일`;
  console.log(dateRange);
  console.log(formattedDate(dateRange[0]));
  console.log(formattedDate(dateRange[1]));
  const [isPloggingList, setPloggingList] = useState<PloggingLog[]>([]);
  const [isSelectedType, setSelectedType] = useState("");

  useEffect(() => {
    searchPloggingUsingDay({
      accessToken: accessToken,
      start_day: formattedDate(dateRange[0]),
      end_day: formattedDate(dateRange[1]),
      success: (res) => {
        console.log("플로깅 일별기록 조회 성공");
        console.log(res);
        setPloggingList(res.data.resultBody);
      },
      fail: (error) => {
        console.error("플로깅 일별기록 조회 실패", error);
      },
    });
  }, []);

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
      {isPloggingList ? (
        <>
          {isPloggingList.map((plogging, index) => (
            <MyPloggingItem key={index} plogging={plogging} />
          ))}
        </>
      ) : null}
    </div>
  );
};

export default MyPloggingList;
