import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { useParams } from "react-router-dom";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewMemberListPage.module.css";
import { MemberInterface } from "interface/crewInterface";
import { getCrewKickOut } from "api/lib/crew";
import { QuestionModal, OkModal } from "components/common/AlertModals";

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
    QuestionModal({ text: "강퇴시키시겠습니까." }).then((res) => {
      if (res.isConfirmed) {
        if (member.crewMemberId !== undefined) {
          getCrewKickOut(
            accessToken,
            Number(crewId),
            member.crewMemberId,
            (res) => {
              // console.log("크루 강퇴 요청 성공");
              // console.log(res.data);
              OkModal({ text: "크루 강퇴가 완료되었습니다." });
              fetchMemberList();
            },
            (err) => {
              // console.log("크루 강퇴 요청 실패", err);
            },
          );
        }
      }
    });
  };
  return (
    <div className={style.Member_Item}>
      <img src={member.profileImage} alt="Crew Image" />
      <div className={style.nickname}>{member.nickname}</div>
      {User.crewinfo.isCrewMaster ? (
        User.info.nickname !== member.nickname ? (
          <Icon
            icon="bi:x"
            style={{
              width: "3rem",
              height: "3rem",
            }}
            className={style.x_Icon}
            onClick={CrewKickOut}
          />
        ) : null
      ) : null}
    </div>
  );
};

export default MemberItem;
