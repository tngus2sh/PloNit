import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewMemberListPage.module.css";
import { MemberInterface } from "interface/crewInterface";
import { getCrewKickOut } from "api/lib/crew";

const MemberItem = ({
  member,
  master,
}: {
  member: MemberInterface;
  master: boolean;
}) => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const CrewKickOut = () => {
    if (member.id !== undefined) {
      getCrewKickOut(
        accessToken,
        Number(crewId),
        member.id,
        (res) => {
          console.log("크루 강퇴 요청 성공");
          console.log(res.data);
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
      {master && (
        <Icon
          icon="bi:x"
          style={{
            width: "3rem",
            height: "3rem",
          }}
          className={style.x_Icon}
          onClick={CrewKickOut}
        />
      )}
    </div>
  );
};

export default MemberItem;
