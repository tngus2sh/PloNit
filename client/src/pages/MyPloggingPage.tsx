import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { BackTopBar } from "components/common/TopBar";
import MyPloggingList from "components/MyPlogging/MyPloggingList";
import "../custom_css/MyPloggingCalendar.css";
import {
  searchPloggingUsingDay,
  searchPloggingUsingMonth,
} from "api/lib/plogging";
import { PloggingLog } from "interface/ploggingInterface";

// const dayList = [
//   "2023-10-10",
//   "2023-10-21",
//   "2023-10-02",
//   "2023-10-14",
//   "2023-10-27",
// ];

// date 형식 변경 (2023-10-27)
const formattedDate = (date: any) => {
  const year = date.getFullYear();
  const month = ("0" + (date.getMonth() + 1)).slice(-2);
  const day = ("0" + date.getDate()).slice(-2);
  return `${year}-${month}-${day}`;
};

const MyPloggingPage = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  // 현재 달력이 몇월인지 저장
  const [isNowMonth, setNowMonth] = useState(new Date().getMonth() + 1);
  console.log(isNowMonth);
  const [isMonthList, setMonthList] = useState("");
  console.log(isMonthList);
  // 선택한 날짜
  const [dateRange, setDateRange] = useState<[Date, Date]>([
    new Date(),
    new Date(),
  ]);
  // 시작 종료 날짜
  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);
  // 해당 날짜의 플로깅 기록 리스트
  const [isPloggingList, setPloggingList] = useState<PloggingLog[]>([]);

  // 시작 종료 날짜를 변경하는 로직
  const handleDateChange = (date: any) => {
    if (!startDate) {
      setStartDate(date);
      setDateRange([date, date]);
    } else {
      if (date < startDate) {
        setStartDate(date);
        setEndDate(date);
        setDateRange([date, date]);
      } else if (endDate && date.getTime() === endDate.getTime()) {
        setStartDate(date);
        setDateRange([date, date]);
      } else {
        setEndDate(date);
        setDateRange([startDate, date]);
      }
    }
  };

  // 플로깅한 날짜에 점 표시
  const tileContent = ({ date, view }: { date: Date; view: string }) => {
    if (isMonthList.includes(date.toISOString().split("T")[0])) {
      return (
        <div
          style={{
            width: "0.5rem",
            height: "0.5rem",
            backgroundColor: "#1a8e3f",
            margin: "auto",
            borderRadius: "50%",
          }}
        ></div>
      );
    }
  };
  useEffect(() => {
    searchPloggingUsingMonth({
      accessToken: accessToken,
      month: isNowMonth,
      success: (res) => {
        console.log("플로깅 월별기록 조회 성공");
        console.log(res.data.resultBody);
        setMonthList(res.data.resultBody);
      },
      fail: (error) => {
        console.error("플로깅 월별기록 조회 실패", error);
      },
    });
  }, [isNowMonth]);

  useEffect(() => {
    searchPloggingUsingDay({
      accessToken: accessToken,
      start_day: formattedDate(dateRange[0]),
      end_day: formattedDate(dateRange[1]),
      success: (res) => {
        console.log("플로깅 일별기록 조회 성공");
        console.log(res.data);
        setPloggingList(res.data.resultBody);
      },
      fail: (error) => {
        console.error("플로깅 일별기록 조회 실패", error);
      },
    });
  }, [dateRange]);
  console.log(isPloggingList);

  return (
    <div>
      <BackTopBar text="나의 플로깅 기록" />
      <Calendar
        locale="en"
        next2Label={null}
        prev2Label={null}
        onChange={handleDateChange}
        value={dateRange}
        tileContent={tileContent}
        calendarType="US"
        onActiveStartDateChange={({ activeStartDate }) => {
          if (activeStartDate) {
            setNowMonth(activeStartDate.getMonth() + 1);
          }
        }}
      />
      <MyPloggingList dateRange={dateRange} PloggingList={isPloggingList} />
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default MyPloggingPage;
