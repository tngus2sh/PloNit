import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import { BackTopBar } from "components/common/TopBar";
import MemberItem from "components/CrewMemberList/MemberItem";
import { MemberInterface } from "interface/crewInterface";
import { getCrewMember, getCrewMaster } from "api/lib/crew";

const CrewMemberListPage = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const [isMemberList, setMemberList] = useState<MemberInterface[]>([]);
  useEffect(() => {
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
  }, []);
  return (
    <div>
      <BackTopBar text="멤버 목록 " />
      {isMemberList.map((member, index) => (
        <MemberItem key={index} member={member} />
      ))}
    </div>
  );
};

export default CrewMemberListPage;
