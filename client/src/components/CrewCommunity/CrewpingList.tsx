import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

import style from "styles/css/CrewCommunityPage/CrewpingList.module.css";

import CrewpingItem from "./CrewpingItem";
import { CrewpingInterface } from "interface/crewInterface";
import { getCrewpingList } from "api/lib/crewping";

const CrewpingList = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const [isCrewpingList, setCrewpingList] = useState<CrewpingInterface[]>([]);

  useEffect(() => {
    getCrewpingList(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("크루 조회 성공");
        console.log(res.data);
        setCrewpingList(res.data.resultBody);
      },
      (err) => {
        console.log("크루 조회 실패", err);
      },
    );
  }, []);

  return (
    <div>
      {isCrewpingList.map((crewping, index) => (
        <CrewpingItem key={index} crewping={crewping} />
      ))}

      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewpingList;
