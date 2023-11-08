import React from "react";
import { useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { MemberInterface } from "interface/crewInterface";
import style from "styles/css/CrewMemberApprovalPage.module.css";
import { getCrewAllow } from "api/lib/crew";

const ApprovalMemberItem = ({
  member,
  fetchMemberList,
}: {
  member: MemberInterface;
  fetchMemberList: () => void;
}) => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const onRegisterApproval = (status: boolean) => {
    const data = {
      crewId: Number(crewId),
      memberId: member.crewMemberId,
      status: status,
    };
    getCrewAllow(
      accessToken,
      data,
      (res) => {
        console.log("크루 가입 승인 성공");
        fetchMemberList();
      },
      (err) => {
        console.log("크루 가입 승인 실패", err);
      },
    );
  };

  return (
    <div className={style.Approval_member_Item}>
      <div className={style.left}>
        <img src={member.profileImage} alt="Crew Image" />
        <div className={style.nickname}>{member.nickname}</div>
      </div>
      <div className={style.right}>
        <div className={style.allow} onClick={() => onRegisterApproval(true)}>
          수락
        </div>
        <div className={style.refuse} onClick={() => onRegisterApproval(false)}>
          거절
        </div>
      </div>
    </div>
  );
};

export default ApprovalMemberItem;
