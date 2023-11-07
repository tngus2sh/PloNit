import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import { BackTopBar } from "components/common/TopBar";
import CrewLeader from "components/CrewDetail/CrewLeader";
import CrewInfo from "components/CrewDetail/CrewInfo";
import CrewRanking from "components/CrewDetail/CrewRanking";
import CrewIntroduce from "components/CrewDetail/CrewIntroduce";
import CommonButton from "components/common/CommonButton";
import { getCrewDetail } from "api/lib/crew";
import { CrewInterface } from "interface/crewInterface";

const CrewDetailPage = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  console.log(crewId);
  const [isCrewDetail, setCrewDetail] = useState<CrewInterface>(
    {} as CrewInterface,
  );

  useEffect(() => {
    getCrewDetail(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("크루 상세 조회 성공");
        console.log(res);
        console.log(res.data);
        console.log(res.data.resultBody);
        setCrewDetail(res.data.resultBody);
      },
      (err) => {
        console.log("크루 상세 조회 실패", err);
      },
    );
  }, []);

  return (
    <div>
      <BackTopBar text="장덕동 플로깅" />
      <div>
        <CrewLeader crew={isCrewDetail} />
        <CrewInfo crew={isCrewDetail} />
        <CrewRanking crew={isCrewDetail} />
        <CrewIntroduce crew={isCrewDetail} />
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
