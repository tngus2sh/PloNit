import React, { useState, useEffect } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { userActions } from "store/user-slice";
import { BackTopBar } from "components/common/TopBar";
import CrewpingLeader from "components/CrewpingDetail/CrewpingLeader";
import CrewpingInfo from "components/CrewpingDetail/CrewpingInfo";
import CrewpingIntroduce from "components/CrewpingDetail/CrewpingIntroduce";
import CommonButton from "components/common/CommonButton";
import { CrewpingInterface } from "interface/crewInterface";
import {
  getCrewpingInfo,
  getCrewpingJoin,
  getCrewpingQuit,
} from "api/lib/crewping";

const CrewpingDetailPage = () => {
  const dispatch = useDispatch();
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const User = useSelector((state: any) => state.user);
  const { crewpingId } = useParams();
  const [isCrewpingInfo, setCrewpingInfo] = useState<CrewpingInterface>(
    {} as CrewpingInterface,
  );

  useEffect(() => {
    fetchCrewpingDetailList();
  }, []);

  const fetchCrewpingDetailList = () => {
    getCrewpingInfo(
      accessToken,
      Number(crewpingId),
      (res) => {
        console.log("크루핑 상세 조회 성공");
        console.log(res.data);
        setCrewpingInfo(res.data.resultBody);
        const data = {
          isMyCrew: User.crewinfo.isMyCrew,
          isCrewMaster: User.crewinfo.isCrewMaster,
          isCrewwpingMaster: res.data.resultBody.masterNickname,
        };
        console.log(data);
        dispatch(userActions.myCrewHandler(data));
      },
      (err) => {
        console.log("크루핑 상세 조회 실패", err);
      },
    );
  };

  const crewpingJoinHandler = () => {
    getCrewpingJoin(
      accessToken,
      Number(crewpingId),
      (res) => {
        console.log(res.data);
        console.log("크루핑 참가 요청 성공");
        alert("크루핑 참가 완료");
        fetchCrewpingDetailList();
      },
      (err) => {
        console.log("크루핑 참가 요청 에러", err);
      },
    );
  };

  const crewpingQuitHandler = () => {
    getCrewpingQuit(
      accessToken,
      Number(crewpingId),
      (res) => {
        console.log(res.data);
        console.log("크루핑 참가 취소 성공");
        alert("크루핑 취소 완료");
        fetchCrewpingDetailList();
      },
      (err) => {
        console.log("크루핑 참가 취소 에러", err);
      },
    );
  };

  return (
    <div>
      <BackTopBar text={isCrewpingInfo.name} />
      <CrewpingLeader crewping={isCrewpingInfo} />
      <CrewpingInfo crewping={isCrewpingInfo} />
      <CrewpingIntroduce crewping={isCrewpingInfo} />
      {User.info.nickname !== isCrewpingInfo.masterNickname ? (
        isCrewpingInfo.isJoined ? (
          <CommonButton
            text="크루핑 취소"
            styles={{
              backgroundColor: "#999999",
            }}
            onClick={crewpingQuitHandler}
          />
        ) : (
          <CommonButton
            text="크루핑 참여"
            styles={{
              backgroundColor: "#2cd261",
            }}
            onClick={crewpingJoinHandler}
          />
        )
      ) : null}
    </div>
  );
};

export default CrewpingDetailPage;
