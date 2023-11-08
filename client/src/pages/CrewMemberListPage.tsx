import React from "react";
import { BackTopBar } from "components/common/TopBar";
import MemberItem from "components/CrewMemberList/MemberItem";
const CrewMemberListPage = () => {
  return (
    <div>
      <BackTopBar text="멤버 목록 " />
      {/* {list.map((member, index) => (
        <MemberItem key={index} />
      ))} */}
      <MemberItem />
      <MemberItem />
      <MemberItem />
    </div>
  );
};

export default CrewMemberListPage;
