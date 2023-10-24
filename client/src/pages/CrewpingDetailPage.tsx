import React from "react";
import { BackTopBar } from "components/common/TopBar";
import CrewpingLeader from "components/CrewpingDetail/CrewpingLeader";
import CrewpingInfo from "components/CrewpingDetail/CrewpingInfo";
import CrewpingIntroduce from "components/CrewpingDetail/CrewpingIntroduce";
import CommonButton from "components/common/CommonButton";

const CrewpingDetailPage = () => {
  return (
    <div>
      <BackTopBar text="장덕동 지킴이" />
      <CrewpingLeader />
      <CrewpingInfo />
      <CrewpingIntroduce />
      <CommonButton
        text="크루핑 참여"
        styles={{
          backgroundColor: "#2cd261",
        }}
      />
      <CommonButton
        text="크루핑 취소"
        styles={{
          backgroundColor: "#999999",
        }}
      />
    </div>
  );
};

export default CrewpingDetailPage;
