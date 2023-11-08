import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import { BackTopBar } from "components/common/TopBar";
import ApprovalMemberItem from "components/CrewMemberApproval/ApprovalMemberItem";
import { MemberInterface } from "interface/crewInterface";
import { getCrewWait } from "api/lib/crew";

const CrewMemberApprovalPage = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const [isApprovalMemberList, setApprovalMemberList] = useState<
    MemberInterface[]
  >([]);

  const fetchMemberList = () => {
    getCrewWait(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("크루원 대기 목록 조회 성공");
        setApprovalMemberList(res.data.resultBody);
      },
      (err) => {
        console.log("크루원 대기 목록 조회 실패", err);
      },
    );
  };

  useEffect(() => {
    fetchMemberList();
  }, []);

  return (
    <div>
      <BackTopBar text="승인 대기 목록 " />
      {isApprovalMemberList.map((member, index) => (
        <ApprovalMemberItem
          key={index}
          member={member}
          fetchMemberList={fetchMemberList}
        />
      ))}
    </div>
  );
};

export default CrewMemberApprovalPage;
