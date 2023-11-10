import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { BackTopBar } from "components/common/TopBar";
import CrewpingLeader from "components/CrewpingDetail/CrewpingLeader";
import CrewpingInfo from "components/CrewpingDetail/CrewpingInfo";
import CrewpingIntroduce from "components/CrewpingDetail/CrewpingIntroduce";
import CommonButton from "components/common/CommonButton";
import { CrewpingInterface } from "interface/crewInterface"; // 경로는 실제 경로에 맞게 조정해주세요.
import { useSelector, useDispatch } from "react-redux";
import {
  getCrewpingInfo,
  getCrewpingJoin,
  getCrewpingQuit,
} from "api/lib/crewping";

const CrewpingDetailPage = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const location = useLocation();
  const { crewpingId } = location.state || {}; // state가 없는 경우를 대비해 기본값 설정
  const [isCrewpingInfo, setCrewpingInfo] = useState<CrewpingInterface>();

  useEffect(() => {
    getCrewpingInfo(
      accessToken,
      Number(crewpingId),
      (res) => {
        console.log("크루핑 상세 조회 성공");
        console.log(res.data);
        setCrewpingInfo(res.data.resultBody);
      },
      (err) => {
        console.log("크루핑 상세 조회 실패", err);
      },
    );
  }, []);

  const crewpingJoinHandler = () => {
    console.log("크루핑 참가 요청 버튼");
    getCrewpingJoin(
      accessToken,
      crewpingId,
      (res) => {
        console.log(res.data);
        console.log("크루핑 참가 요청 성공");
      },
      (err) => {
        console.log("크루핑 참가 요청 에러", err);
      },
    );
  };

  const crewpingQuitHandler = () => {
    console.log("크루핑 참가 취소 버튼");
    getCrewpingQuit(
      accessToken,
      crewpingId,
      (res) => {
        console.log(res.data);
        console.log("크루핑 참가 취소 성공");
      },
      (err) => {
        console.log("크루핑 참가 취소 에러", err);
      },
    );
  };

  return (
    <div>
      {isCrewpingInfo && <BackTopBar text={isCrewpingInfo.name} />}
      {isCrewpingInfo && <CrewpingLeader crewping={isCrewpingInfo} />}
      {isCrewpingInfo && <CrewpingInfo crewping={isCrewpingInfo} />}
      {isCrewpingInfo && <CrewpingIntroduce crewping={isCrewpingInfo} />}

      <CommonButton
        text="크루핑 참여"
        styles={{
          backgroundColor: "#2cd261",
        }}
        onClick={crewpingJoinHandler}
      />
      <CommonButton
        text="크루핑 취소"
        styles={{
          backgroundColor: "#999999",
        }}
        onClick={crewpingQuitHandler}
      />
    </div>
  );
};

export default CrewpingDetailPage;
