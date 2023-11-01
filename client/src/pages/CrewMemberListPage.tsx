import React from "react";
import { BackTopBar } from "components/common/TopBar";
import MemberList from "components/CrewList/MemberList";
const CrewMemberListPage = () => {
  return (
    <div>
      <BackTopBar text="멤버 목록 " />
      <MemberList />
    </div>
  );
};

export default CrewMemberListPage;
