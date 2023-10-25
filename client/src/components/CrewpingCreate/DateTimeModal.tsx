import React, { useState } from "react";
import style from "styles/css/CrewpingCreatePage/DateTimeModal.module.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

interface DateTimeModalProps {
  onClose: () => void;
}

function DateTimeModal({ onClose }: DateTimeModalProps) {
  const [startDate, setStartDate] = useState<Date | null>(new Date());

  return (
    <div className={style.modal}>
      <div className={style.modalContent}>
        <DatePicker
          selected={startDate}
          onChange={(date) => setStartDate(date || new Date())}
          dateFormat="yyyy-MM-dd"
        />
        시간시간
        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
}

export default DateTimeModal;
