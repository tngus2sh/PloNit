import React, { useState } from "react";
import { Icon } from "@iconify/react";
import DatePicker, { registerLocale } from "react-datepicker";
import ko from "date-fns/locale/ko";
// import "react-datepicker/dist/react-datepicker.css";
import style from "styles/css/CrewpingCreatePage.module.css";
import "custom_css/CrewCreateDatePicker.css";

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
  console.log(selectedStartDate);
  console.log(selectedEndDate);
  const onChangeStartDate = (date: any) => {
    setSelectedStartDate(date);
    setCrewpingStartDate(formattedDate(date));
    setSelectedEndDate(date);
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
        timeIntervals={30}
        name="date_time"
        id="date_time"
        placeholderText="시작 일시"
        onChange={onChangeStartDate}
        dateFormat="yyyy-MM-dd HH:mm"
        locale={ko}
        showPopperArrow={false}
        fixedHeight
        renderCustomHeader={({
          date,
          decreaseMonth,
          increaseMonth,
          prevMonthButtonDisabled,
          nextMonthButtonDisabled,
        }) => (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              fontSize: "1.1rem",
            }}
            className="custom-react-datepicker__select-wrapper"
          >
            <button
              onClick={decreaseMonth}
              disabled={prevMonthButtonDisabled}
              className="custom_btn"
            >
              <Icon
                icon="bi:chevron-left"
                style={{ width: "1.5rem", height: "1.5rem" }}
              />
            </button>
            <div>
              {date.getFullYear()}년 {date.getMonth()}월
            </div>
            <button
              onClick={increaseMonth}
              disabled={nextMonthButtonDisabled}
              className="custom_btn"
            >
              <Icon
                icon="bi:chevron-right"
                style={{ width: "1.5rem", height: "1.5rem" }}
              />
            </button>
          </div>
        )}
      />
      <DatePicker
        selected={selectedEndDate}
        className={style.datepicker2}
        minDate={selectedStartDate}
        showTimeSelect
        timeIntervals={30}
        name="date_time"
        id="date_time"
        placeholderText="종료 일시"
        onChange={onChangeEndDate}
        dateFormat="yyyy-MM-dd HH:mm"
        locale={ko}
        showPopperArrow={false}
        fixedHeight
        disabled={!selectedStartDate}
        renderCustomHeader={({
          date,
          decreaseMonth,
          increaseMonth,
          prevMonthButtonDisabled,
          nextMonthButtonDisabled,
        }) => (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              fontSize: "1.1rem",
            }}
            className="custom-react-datepicker__select-wrapper"
          >
            <button
              onClick={decreaseMonth}
              disabled={prevMonthButtonDisabled}
              className="custom_btn"
            >
              <Icon
                icon="bi:chevron-left"
                style={{ width: "1.5rem", height: "1.5rem" }}
              />
            </button>
            <div>
              {date.getFullYear()}년 {date.getMonth()}월
            </div>
            <button
              onClick={increaseMonth}
              disabled={nextMonthButtonDisabled}
              className="custom_btn"
            >
              <Icon
                icon="bi:chevron-right"
                style={{ width: "1.5rem", height: "1.5rem" }}
              />
            </button>
          </div>
        )}
      />
    </div>
  );
};

export default CrewpingDate;
