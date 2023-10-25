import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import style from "styles/css/CrewpingCreatePage.module.css";

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
        className={style.inputBox}
        name="date_time"
        id="date_time"
        onChange={(date) => setSelectedStartDate(date)}
        dateFormat="yyyy.MM.dd"
      />
      <DatePicker
        selected={selectedEndDate}
        className={style.inputBox2}
        name="date_time"
        id="date_time"
        onChange={(date) => setSelectedEndDate(date)}
        dateFormat="yyyy.MM.dd"
      />
    </div>
  );
};

export default CrewpingDate;
