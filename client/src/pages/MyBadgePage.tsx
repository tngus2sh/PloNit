import React from "react";
import { BackTopBar } from "components/common/TopBar";
import CustomTab from "components/common/CustomTab";
import RankingBadge from "components/MyBadge/RankingBadge";
import MissionBadge from "components/MyBadge/MissionBadge";

const MyBadgePage = () => {
  const tabProps = {
    미션: <MissionBadge />,
    랭킹: <RankingBadge />,
  };

  return (
    <div>
      <BackTopBar text="나의 뱃지" />
      <CustomTab tabProps={tabProps} />
    </div>
  );
};

export default MyBadgePage;
