import React from "react";
import { BackTopBar } from "components/common/TopBar";
import MyCrewList from "components/CrewList/MyCrewList";

const MyCrewPage = () => {
  return (
    <div>
      <BackTopBar text="나의 크루" />
      <MyCrewList />
    </div>
  );
};

export default MyCrewPage;
