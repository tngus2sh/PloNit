import React from "react";
import { BackTopBar } from "components/common/TopBar";
import CrewLeader from "components/CrewDetail/CrewLeader";
import CrewInfo from "components/CrewDetail/CrewInfo";
import CrewRanking from "components/CrewDetail/CrewRanking";
import CrewIntroduce from "components/CrewDetail/CrewIntroduce";
import CommonButton from "components/common/CommonButton";

const CrewDetailPage = () => {
  return (
    <div>
      <BackTopBar text="장덕동 플로깅" />
      <div>
        <CrewLeader />
        <CrewInfo />
        <CrewRanking />
        <CrewIntroduce />
      </div>
      <CommonButton
        text="크루 탈퇴"
        styles={{
          backgroundColor: "gray",
        }}
      />
    </div>
  );
};

export default CrewDetailPage;
