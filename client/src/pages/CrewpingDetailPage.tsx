import React from "react";
import { BackTopBar } from "components/common/TopBar";
import CrewpingLeader from "components/CrewpingDetail/CrewpingLeader";
import CrewpingInfo from "components/CrewpingDetail/CrewpingInfo";
import CrewpingIntroduce from "components/CrewpingDetail/CrewpingIntroduce";

const CrewpingDetailPage = () => {
  return (
    <div>
      <BackTopBar text="장덕동 지킴이" />
      <CrewpingLeader />
      <CrewpingInfo />
      <CrewpingIntroduce />
    </div>
  );
};

export default CrewpingDetailPage;
