import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

import style from "styles/css/CrewCommunityPage/CrewpingList.module.css";

import CrewpingItem from "./CrewpingItem";
import { CrewpingInterface } from "interface/crewInterface";
import { getCrewpingList } from "api/lib/crewping";

const CrewpingList = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const { crewId } = useParams();
  const [isCrewpingList, setCrewpingList] = useState<CrewpingInterface[]>([]);

  useEffect(() => {
    getCrewpingList(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("크루핑 조회 성공");
        console.log(res.data);
        setCrewpingList(res.data.resultBody);
      },
      (err) => {
        console.log("크루핑 조회 실패", err);
      },
    );
  }, []);

  return (
    <div>
      {isCrewpingList ? (
        <>
          {isCrewpingList.map((crewping, index) => (
            <CrewpingItem key={index} crewping={crewping} />
          ))}
        </>
      ) : (
        <div style={{ marginTop: "2rem" }}>크루핑이 존재하지 않습니다.</div>
      )}
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewpingList;
