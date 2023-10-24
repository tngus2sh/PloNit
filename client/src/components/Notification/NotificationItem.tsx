import React from "react";
import style from "styles/css/NotificationPage/NotificationItem.module.css";

const NotificationItem = () => {
  return (
    <div className={style.notification_item}>
      <div className={style.alarm_icon}>
        <img src="/metamong.png" alt="몽" />
      </div>
      <div className={style.alarm_section}>
        <div className={style.alarm_top}>
          <div>크루핑 완료</div>
          <div className={style.alarm_date}>10월 18일</div>
        </div>
        <div className={style.alarm_title}>
          장덕동 지킴이 크루핑이 완료되었습니다.장덕동 지킴이 크루핑이
          완료되었습니다.
        </div>
      </div>
    </div>
  );
};

export default NotificationItem;
