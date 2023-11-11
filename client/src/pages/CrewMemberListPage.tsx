import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import { BackTopBar } from "components/common/TopBar";
import MemberItem from "components/CrewMemberList/MemberItem";
import { CrewInterface, MemberInterface } from "interface/crewInterface";
import { getCrewDetail, getCrewMember, getCrewMaster } from "api/lib/crew";

const CrewMemberListPage = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const { crewId } = useParams();
  const User = useSelector((state: any) => state.user);
  const [isMemberList, setMemberList] = useState<MemberInterface[]>([]);

  const fetchMemberList = () => {
    if (User.crewinfo.isCrewMaster) {
      getCrewMaster(
        accessToken,
        Number(crewId),
        (res) => {
          console.log(res.data);
          setMemberList(res.data.resultBody);
          console.log("크루장 목록조회 성공");
        },
        (err) => {
          console.log("크루장 목록조회 실패", err);
        },
      );
    } else {
      getCrewMember(
        accessToken,
        Number(crewId),
        (res) => {
          console.log(res.data);
          setMemberList(res.data.resultBody);
          console.log("크루원 목록조회 성공");
        },
        (err) => {
          console.log("크루원 목록조회 실패", err);
        },
      );
    }
  };

  useEffect(() => {
    fetchMemberList();
  }, []);

  return (
    <div>
      <BackTopBar text="멤버 목록 " />
      {isMemberList.map((member, index) => (
        <MemberItem
          key={index}
          member={member}
          fetchMemberList={fetchMemberList}
        />
      ))}
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewMemberListPage;
