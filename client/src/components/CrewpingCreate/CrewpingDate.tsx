import React, { useState } from "react";
import DatePicker from "react-datepicker";
// import "styles/CrewCreateDatePicker.css";
import style from "styles/css/CrewpingCreatePage.module.css";
import getYear from "date-fns/getYear";
import getMonth from "date-fns/getMonth";
import "custom_css/CrewCreateDatePicker.css";

const months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];

const CrewpingDate = () => {
  const [selectedStartDate, setSelectedStartDate] = useState<Date | null>(null);
  const [selectedEndDate, setSelectedEndDate] = useState<Date | null>(null);
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
        onChange={(date) => setSelectedStartDate(date)}
        dateFormat="yyyy.MM.dd hh:mm"
      />
      <DatePicker
        selected={selectedEndDate}
        className={style.datepicker2}
        minDate={selectedStartDate}
        showTimeSelect
        name="date_time"
        id="date_time"
        placeholderText="종료 일시"
        onChange={(date) => setSelectedEndDate(date)}
        dateFormat="yyyy.MM.dd hh:mm"
      />
    </div>
  );
};

export default CrewpingDate;
