import React from "react";
import { BasicTopBar } from "components/common/TopBar";
import CustomTab from "components/common/CustomTab";
import MyCrewList from "components/CrewList/MyCrewList";
import TotalCrewList from "components/CrewList/TotalCrewList";

const CrewListPage = () => {
  const tabProps = {
    나의크루목록: <MyCrewList />,
    전체크루목록: <TotalCrewList />,
  };

  return (
    <div>
      <BasicTopBar text="크루 목록" />
      <CustomTab tabProps={tabProps} />
    </div>
  );
};

export default CrewListPage;
