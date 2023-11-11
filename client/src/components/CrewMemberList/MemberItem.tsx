import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewMemberListPage.module.css";
import { MemberInterface } from "interface/crewInterface";
import { getCrewKickOut } from "api/lib/crew";

const MemberItem = ({
  member,
  fetchMemberList,
}: {
  member: MemberInterface;
  fetchMemberList: () => void;
}) => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const User = useSelector((state: any) => state.user);
  const { crewId } = useParams();
  const CrewKickOut = () => {
    if (member.crewMemberId !== undefined) {
      getCrewKickOut(
        accessToken,
        Number(crewId),
        member.crewMemberId,
        (res) => {
          console.log("크루 강퇴 요청 성공");
          console.log(res.data);
          fetchMemberList();
        },
        (err) => {
          console.log("크루 강퇴 요청 실패", err);
        },
      );
    }
  };
  return (
    <div className={style.Member_Item}>
      <img src={member.profileImage} alt="Crew Image" />
      <div className={style.nickname}>{member.nickname}</div>
      {User.crewinfo.isCrewMaster ? (
        <Icon
          icon="bi:x"
          style={{
            width: "3rem",
            height: "3rem",
          }}
          className={style.x_Icon}
          onClick={CrewKickOut}
        />
      ) : null}
    </div>
  );
};

export default MemberItem;
