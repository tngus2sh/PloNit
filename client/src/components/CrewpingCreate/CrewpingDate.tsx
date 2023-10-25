import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import style from "styles/css/CrewpingCreatePage.module.css";
import getYear from "date-fns/getYear";
import getMonth from "date-fns/getMonth";

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
        dateFormat="yyyy.MM.dd"
        renderCustomHeader={({
          date,
          prevMonthButtonDisabled,
          nextMonthButtonDisabled,
          decreaseMonth,
          increaseMonth,
        }) => (
          <div
            style={{
              margin: "0.5rem",
              display: "flex",
              justifyContent: "center",
              fontSize: "1.8rem",
            }}
          >
            <button
              className={`btn_month btn_month-prev ${
                prevMonthButtonDisabled ? "disabled" : ""
              }`}
              onClick={decreaseMonth}
              disabled={prevMonthButtonDisabled}
            >
              <img src="/static/images/arrow-black-left.png" />
            </button>
            <div className="month-day">
              {getYear(date)}.{months[getMonth(date)]}
            </div>
            <button
              className={`btn_month btn_month-next ${
                nextMonthButtonDisabled ? "disabled" : ""
              }`}
              onClick={nextMonthButtonDisabled ? undefined : increaseMonth}
              disabled={nextMonthButtonDisabled}
            >
              <img src="/static/images/arrow-black-right.png" />
            </button>
          </div>
        )}
      />
      <DatePicker
        selected={selectedEndDate}
        className={style.datepicker2}
        minDate={selectedStartDate}
        name="date_time"
        id="date_time"
        placeholderText="종료 일시"
        onChange={(date) => setSelectedEndDate(date)}
        dateFormat="yyyy.MM.dd"
        renderCustomHeader={({
          date,
          prevMonthButtonDisabled,
          nextMonthButtonDisabled,
          decreaseMonth,
          increaseMonth,
        }) => (
          <div
            style={{
              margin: "0.5rem",
              display: "flex",
              justifyContent: "center",
              fontSize: "1.8rem",
            }}
          >
            <button
              className={`btn_month btn_month-prev ${
                prevMonthButtonDisabled ? "disabled" : ""
              }`}
              onClick={decreaseMonth}
              disabled={prevMonthButtonDisabled}
            >
              <img src="/static/images/arrow-black-left.png" />
            </button>
            <div className="month-day">
              {getYear(date)}.{months[getMonth(date)]}
            </div>
            <button
              className={`btn_month btn_month-next ${
                nextMonthButtonDisabled ? "disabled" : ""
              }`}
              onClick={nextMonthButtonDisabled ? undefined : increaseMonth}
              disabled={nextMonthButtonDisabled}
            >
              <img src="/static/images/arrow-black-right.png" />
            </button>
          </div>
        )}
      />
    </div>
  );
};

export default CrewpingDate;
