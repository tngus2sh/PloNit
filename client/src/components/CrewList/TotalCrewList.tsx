import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import style from "styles/css/CrewList/CrewListItem.module.css";
import { Icon } from "@iconify/react";
import Input from "components/common/Input";
import CrewItem from "./CrewItem";
import * as Interfaces from "interface/crewInterface";
import { getCrewList } from "api/lib/crew";

const TotalCrewList = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [totalCrewList, setTotalCrewList] = useState<
    Interfaces.CrewInterface[]
  >([]);
  const [inputValue, setInputValue] = useState<string>("");

  useEffect(() => {
    getCrewList(
      accessToken,
      (res) => {
        console.log(res.data);
        setTotalCrewList(res.data.resultBody);
        console.error("크루 목록조회 성공");
      },
      (err) => {
        console.error("크루 목록조회 실패", err);
      },
    );
  }, []);

  return (
    <div>
      {/* {진짜 검색창으로 바꿀것} */}
      <Input
        id="crew_name"
        type="text"
        value={inputValue}
        placeholder="크루명, 지역으로 검색"
      />

      {totalCrewList.map((crew, index) => (
        <CrewItem key={index} crew={crew} />
      ))}
    </div>
  );
};

export default TotalCrewList;
