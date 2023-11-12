import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import style from "styles/css/CrewpingCreatePage.module.css";

const formattedDate = (date: any) => {
  const year = date.getFullYear();
  const month = ("0" + (date.getMonth() + 1)).slice(-2);
  const day = ("0" + date.getDate()).slice(-2);
  const hours = ("0" + date.getHours()).slice(-2);
  const minutes = ("0" + date.getMinutes()).slice(-2);
  return `${year}-${month}-${day} ${hours}:${minutes}`;
};

const CrewpingDate = ({ setCrewpingStartDate, setCrewpingEndDate }: any) => {
  const [selectedStartDate, setSelectedStartDate] = useState(null);
  const [selectedEndDate, setSelectedEndDate] = useState(null);
  const onChangeStartDate = (date: any) => {
    setSelectedStartDate(date);
    setCrewpingStartDate(formattedDate(date));
  };
  const onChangeEndDate = (date: any) => {
    setSelectedEndDate(date);
    setCrewpingEndDate(formattedDate(date));
  };
  return (
    <div className={style.date_time}>
      <label className={style.label} htmlFor="date_time">
        시간
      </label>
      <DatePicker
        selected={selectedStartDate}
        className={style.datepicker1}
        minDate={new Date()}
        showTimeSelect
        name="date_time"
        id="date_time"
        placeholderText="시작 일시"
        onChange={onChangeStartDate}
        dateFormat="yyyy-MM-dd HH:mm"
      />
      <DatePicker
        selected={selectedEndDate}
        className={style.datepicker2}
        minDate={selectedStartDate}
        showTimeSelect
        name="date_time"
        id="date_time"
        placeholderText="종료 일시"
        onChange={onChangeEndDate}
        dateFormat="yyyy-MM-dd HH:mm"
      />
    </div>
  );
};

export default CrewpingDate;
