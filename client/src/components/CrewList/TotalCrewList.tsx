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
        setTotalCrewList(res.data);
        console.error("크루 목록조회 성공");
      },
      (err) => {
        console.error("크루 목록조회 실패", err);
      },
    );
  }, []);

  const list = [
    {
      name: "하남동 플로깅 크루",
      region: "광산구 장덕동",
      cntPeople: 25,
      crewImage: "your-image-url-1.jpg",
    },
    {
      name: "다른 크루 이름",
      region: "다른 위치",
      cntPeople: 20,
      crewImage: "your-image-url-2.jpg",
    },
    {
      name: "다른 크루 이름",
      region: "다른 위치",
      cntPeople: 20,
      crewImage: "your-image-url-2.jpg",
    },
    // ... 추가적으로 더 많은 아이템들을 넣을 수 있습니다.
  ];

  return (
    <div>
      {/* {진짜 검색창으로 바꿀것} */}
      <Input
        id="crew_name"
        type="text"
        value={inputValue}
        placeholder="크루명, 지역으로 검색"
      />

      {list.map((crew, index) => (
        <CrewItem key={index} crew={crew} />
      ))}
    </div>
  );
};

export default TotalCrewList;
