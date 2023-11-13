import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { CrewpingInterface, MemberInterface } from "interface/crewInterface";
import {
  getCrewpingMemberList,
  getCrewpingMemberListMaster,
} from "api/lib/crewping";
import style from "styles/css/CrewpingDetailPage/CrewpingInfo.module.css";
import { Icon } from "@iconify/react";

const CrewpingMemberList = ({ crewping }: { crewping: CrewpingInterface }) => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const User = useSelector((state: any) => state.user);
  const [isCrewpingMemberList, setCrewpingMemberList] = useState<
    MemberInterface[]
  >([]);
  const { crewpingId } = useParams();
  console.log(crewping);

  useEffect(() => {
    console.log(crewping.masterNickname);
    console.log(User.info.nickname);
    if (crewping.masterNickname === User.info.nickname) {
      getCrewpingMemberListMaster(
        accessToken,
        Number(crewpingId),
        (res) => {
          console.log("크루핑멤버 마스터 상세 조회 성공");
          console.log(res.data);
          setCrewpingMemberList(res.data.resultBody);
        },
        (err) => {
          console.log("크루핑멤버 마스터 상세 조회 실패", err);
        },
      );
    } else {
      getCrewpingMemberList(
        accessToken,
        Number(crewpingId),
        (res) => {
          console.log("크루핑멤버 상세 조회 성공");
          console.log(res.data);
          setCrewpingMemberList(res.data.resultBody);
        },
        (err) => {
          console.log("크루핑멤버 상세 조회 실패", err);
        },
      );
    }
  }, []);
  console.log(isCrewpingMemberList);
  return (
    <div className={style.member_list}>
      {isCrewpingMemberList.map((member, index) => (
        <div className={style.member_item} key={index}>
          <img src={member.profileImage} alt="" />
          <div className={style.nickname}>{member.nickname}</div>
          {User.crewinfo.isCrewpingMaster === User.info.nickname ? (
            User.crewinfo.isCrewpingMaster === crewping.masterNickname ? (
              <Icon
                icon="bi:x"
                className={style.icon}
                style={{ height: "1.5rem", width: "1.5rem", color: "black" }}
              />
            ) : null
          ) : null}
        </div>
      ))}
    </div>
  );
};

export default CrewpingMemberList;
