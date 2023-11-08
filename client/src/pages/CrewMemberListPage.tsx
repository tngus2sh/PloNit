import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import { BackTopBar } from "components/common/TopBar";
import MemberItem from "components/CrewMemberList/MemberItem";
import { CrewInterface, MemberInterface } from "interface/crewInterface";
import { getCrewDetail, getCrewMember, getCrewMaster } from "api/lib/crew";

const CrewMemberListPage = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const [isCrewDetail, setCrewDetail] = useState<CrewInterface>(
    {} as CrewInterface,
  );
  const [isMemberList, setMemberList] = useState<MemberInterface[]>([]);

  useEffect(() => {
    getCrewDetail(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("크루 상세 조회 성공");
        setCrewDetail(res.data.resultBody);
        if (!isCrewDetail.isCrewMaster) {
          getCrewMember(
            accessToken,
            Number(crewId),
            (res) => {
              console.log(res.data);
              setMemberList(res.data.resultBody);
              console.error("크루원 목록조회 성공");
            },
            (err) => {
              console.error("크루원 목록조회 실패", err);
            },
          );
        } else {
          getCrewMaster(
            accessToken,
            Number(crewId),
            (res) => {
              console.log(res.data);
              setMemberList(res.data.resultBody);
              console.error("크루장 목록조회 성공");
            },
            (err) => {
              console.error("크루장 목록조회 실패", err);
            },
          );
        }
      },
      (err) => {
        console.log("크루 상세 조회 실패", err);
      },
    );
  }, []);

  return (
    <div>
      <BackTopBar text="멤버 목록 " />
      {isMemberList.map((member, index) => (
        <MemberItem
          key={index}
          member={member}
          master={isCrewDetail.isCrewMaster || false}
        />
      ))}
    </div>
  );
};

export default CrewMemberListPage;
