import React, { useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { BackTopBar } from "components/common/TopBar";
import MyPloggingList from "components/MyPlogging/MyPloggingList";
import "../custom_css/MyPloggingCalendar.css";

const dayList = [
  "2023-10-10",
  "2023-10-21",
  "2023-10-02",
  "2023-10-14",
  "2023-10-27",
];

const MyPloggingPage = () => {
  const [dateRange, setDateRange] = useState<[Date, Date]>([
    new Date(),
    new Date(),
  ]);

  const [startDate, setStartDate] = useState<Date | null>(null);
  const [endDate, setEndDate] = useState<Date | null>(null);

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

  const tileContent = ({ date, view }: { date: Date; view: string }) => {
    if (dayList.includes(date.toISOString().split("T")[0])) {
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
      />

      <MyPloggingList dateRange={dateRange} />
    </div>
  );
};

export default MyPloggingPage;
