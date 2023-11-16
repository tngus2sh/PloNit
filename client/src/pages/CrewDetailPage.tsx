import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { BackTopBar } from "components/common/TopBar";
import CrewLeader from "components/CrewDetail/CrewLeader";
import CrewInfo from "components/CrewDetail/CrewInfo";
import CrewRanking from "components/CrewDetail/CrewRanking";
import CrewIntroduce from "components/CrewDetail/CrewIntroduce";
import CommonButton from "components/common/CommonButton";
import { getCrewDetail, getCrewQuit } from "api/lib/crew";
import { CrewInterface } from "interface/crewInterface";
import {
  QuestionModal,
  OkModal,
  NotOkModal,
} from "components/common/AlertModals";

const CrewDetailPage = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const User = useSelector((state: any) => state.user);
  const { crewId } = useParams();
  const [isCrewDetail, setCrewDetail] = useState<CrewInterface>(
    {} as CrewInterface,
  );

  useEffect(() => {
    getCrewDetail(
      accessToken,
      Number(crewId),
      (res) => {
        // console.log("크루 상세 조회 성공");
        // console.log(res.data);
        setCrewDetail(res.data.resultBody);
      },
      (err) => {
        console.log("크루 상세 조회 실패", err);
      },
    );
  }, []);

  const CrewQuit = () => {
    QuestionModal({ text: "크루를 탈퇴하시겠습니까" }).then((res) => {
      if (res.isConfirmed) {
        getCrewQuit(
          accessToken,
          Number(crewId),
          (res) => {
            // console.log("크루 탈퇴 요청 성공");
            // console.log(res.data);
            // OkModal({ text: "크루 탈퇴가 완료되었습니다." });
            navigate(`/crew/community/${crewId}`);
          },
          (err) => {
            console.log("크루 탈퇴 요청 실패", err);
            // NotOkModal({ text: "크루 탈퇴가 실패했습니다." });
          },
        );
      }
    });
  };
  return (
    <div>
      <BackTopBar text={isCrewDetail.name} />
      <div>
        <CrewLeader crew={isCrewDetail} />
        <CrewInfo crew={isCrewDetail} />
        <CrewRanking crew={isCrewDetail} />
        <CrewIntroduce crew={isCrewDetail} />
      </div>
      {!User.crewinfo.isCrewMaster ? (
        isCrewDetail.isMyCrew ? (
          <CommonButton
            text="크루 탈퇴"
            styles={{
              backgroundColor: "gray",
            }}
            onClick={CrewQuit}
          />
        ) : null
      ) : null}
    </div>
  );
};

export default CrewDetailPage;
