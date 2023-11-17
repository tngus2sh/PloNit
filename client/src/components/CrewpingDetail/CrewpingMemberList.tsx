import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { CrewpingInterface, MemberInterface } from "interface/crewInterface";
import {
  getCrewpingMemberList,
  getCrewpingMemberListMaster,
} from "api/lib/crewping";
import style from "styles/css/CrewpingDetailPage/CrewpingInfo.module.css";
import CrewpingMemberItem from "./CrewpingMemberItem";

const CrewpingMemberList = ({
  crewping,
  fetchCrewpingDetailList,
}: {
  crewping: CrewpingInterface;
  fetchCrewpingDetailList: () => void;
}) => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const User = useSelector((state: any) => state.user);
  const [isCrewpingMemberList, setCrewpingMemberList] = useState<
    MemberInterface[]
  >([]);
  const { crewpingId } = useParams();

  useEffect(() => {
    if (crewping.masterNickname === User.info.nickname) {
      getCrewpingMemberListMaster(
        accessToken,
        Number(crewpingId),
        (res) => {
          // console.log("크루핑멤버 마스터 상세 조회 성공");
          // console.log(res.data);
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
          // console.log("크루핑멤버 상세 조회 성공");
          // console.log(res.data);
          setCrewpingMemberList(res.data.resultBody);
        },
        (err) => {
          console.log("크루핑멤버 상세 조회 실패", err);
        },
      );
    }
  }, []);

  return (
    <div className={style.member_list}>
      {isCrewpingMemberList.map((member, index) => (
        <CrewpingMemberItem
          key={index}
          member={member}
          crewping={crewping}
          fetchCrewpingDetailList={fetchCrewpingDetailList}
        />
      ))}
    </div>
  );
};

export default CrewpingMemberList;
