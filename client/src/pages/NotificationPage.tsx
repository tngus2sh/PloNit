import React from "react";
import { BackTopBar } from "components/common/TopBar";
import NotificationItem from "components/Notification/NotificationItem";

const NotificationPage = () => {
  return (
    <div>
      <BackTopBar text="알림" />
      <NotificationItem />
      <NotificationItem />
    </div>
  );
};

export default NotificationPage;
