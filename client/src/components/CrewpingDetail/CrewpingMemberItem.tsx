import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { CrewpingInterface, MemberInterface } from "interface/crewInterface";
import style from "styles/css/CrewpingDetailPage/CrewpingInfo.module.css";
import { Icon } from "@iconify/react";
import { getCrewpingKickOut } from "api/lib/crewping";
import { QuestionModal, OkModal } from "components/common/AlertModals";

const CrewpingMemberItem = ({
  member,
  crewping,
}: {
  member: MemberInterface;
  crewping: CrewpingInterface;
}) => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const User = useSelector((state: any) => state.user);
  console.log(member);
  const { crewpingId } = useParams();

  const CrewpingKickOut = () => {
    QuestionModal({ text: "강퇴시키시겠습니까." }).then((res) => {
      if (res.isConfirmed) {
        if (member.id !== undefined) {
          getCrewpingKickOut(
            accessToken,
            Number(crewpingId),
            member.id,
            (res) => {
              console.log("크루 강퇴 요청 성공");
              console.log(res.data);
              OkModal({ text: "크루 강퇴가 완료되었습니다." });
            },
            (err) => {
              console.log("크루 강퇴 요청 실패", err);
            },
          );
        }
      }
    });
  };

  return (
    <div className={style.member_item}>
      <img src={member.profileImage} alt="" />
      <div className={style.nickname}>{member.nickname}</div>
      {User.crewinfo.isCrewpingMaster === User.info.nickname ? (
        member.nickname !== crewping.masterNickname ? (
          <Icon
            icon="bi:x"
            className={style.icon}
            style={{ height: "1.5rem", width: "1.5rem", color: "black" }}
            onClick={CrewpingKickOut}
          />
        ) : null
      ) : null}
    </div>
  );
};

export default CrewpingMemberItem;
