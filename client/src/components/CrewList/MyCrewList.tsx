import React from "react";
import style from "styles/css/CrewList/CrewListItem.module.css";
import CrewItem from "./CrewItem";

const MyCrewList = () => {
  const list = [
    {
      name: "하남동 플로깅 크루",
      region: "광산구 장덕동",
      cntPeople: 25,
      crewImage: "your-image-url-1.jpg",
    },
    {
      name: "장덕동",
      region: "다른 위치",
      cntPeople: 20,
      crewImage: "your-image-url-2.jpg",
    },
  ];

  return (
    <div>
      {list.map((crew, index) => (
        <CrewItem key={index} crew={crew} />
      ))}
    </div>
  );
};

export default MyCrewList;
