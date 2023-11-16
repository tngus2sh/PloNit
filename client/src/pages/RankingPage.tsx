import React, { useState, useEffect } from "react";
import { BasicTopBar } from "components/common/TopBar";
import CustomTab from "components/common/CustomTab";
import PersonalRanking from "components/Ranking/PersonalRanking";
import CrewTotalRanking from "components/Ranking/CrewTotalRanking";
import CrewAverageRanking from "components/Ranking/CrewAverageRanking";

const RankingPage = () => {
  const tabProps = {
    개인: <PersonalRanking />,
    "크루 전체": <CrewTotalRanking />,
    "크루 평균": <CrewAverageRanking />,
  };

  return (
    <div>
      <BasicTopBar text="랭킹" />
      <CustomTab tabProps={tabProps} />
    </div>
  );
};

export default RankingPage;
