import React from "react";
import { useLocation } from "react-router-dom";
import { BackTopBar } from "components/common/TopBar";
import CrewpingLeader from "components/CrewpingDetail/CrewpingLeader";
import CrewpingInfo from "components/CrewpingDetail/CrewpingInfo";
import CrewpingIntroduce from "components/CrewpingDetail/CrewpingIntroduce";
import CommonButton from "components/common/CommonButton";
import { CrewpingInterface } from "interface/crewInterface"; // 경로는 실제 경로에 맞게 조정해주세요.

const CrewpingDetailPage = () => {
  const location = useLocation();
  const { crewping } = location.state || {}; // state가 없는 경우를 대비해 기본값 설정

  return (
    <div>
      <BackTopBar text={crewping.name} />
      <CrewpingLeader crewping={crewping} />
      <CrewpingInfo crewping={crewping} />
      <CrewpingIntroduce crewping={crewping} />
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
