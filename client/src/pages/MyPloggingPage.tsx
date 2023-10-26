import React, { useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { BackTopBar } from "components/common/TopBar";
import MyPloggingList from "components/MyPlogging/MyPloggingList";

const MyPloggingPage = () => {
  const [today, setToday] = useState(new Date());
  const onChangeToday = () => {
    setToday(today);
  };
  return (
    <div>
      <BackTopBar text="나의 플로깅 기록" />
      <Calendar onChange={onChangeToday} value={today} />
      <MyPloggingList />
    </div>
  );
};

export default MyPloggingPage;
