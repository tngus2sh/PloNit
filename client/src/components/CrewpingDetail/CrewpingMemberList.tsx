import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { CrewpingInterface, MemberInterface } from "interface/crewInterface";
import { getCrewpingMemberList } from "api/lib/crewping";

const CrewpingMemberList = ({ crewping }: { crewping: CrewpingInterface }) => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [isCrewpingMember, setCrewpingMember] = useState<MemberInterface>(
    {} as MemberInterface,
  );
  useEffect(() => {
    getCrewpingMemberList(
      accessToken,
      crewping.id,
      (res) => {
        console.log("크루핑멤버 상세 조회 성공");
        console.log(res.data);
        setCrewpingMember(res.data.resultBody);
      },
      (err) => {
        console.log("크루핑멤버 상세 조회 실패", err);
      },
    );
  }, []);
  console.log(isCrewpingMember);
  return (
    <div>
      <div>사람</div>
    </div>
  );
};

export default CrewpingMemberList;
