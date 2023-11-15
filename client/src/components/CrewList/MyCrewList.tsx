import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import style from "styles/css/CrewList/CrewListItem.module.css";
import CrewItem from "./CrewItem";
import { CrewInterface } from "interface/crewInterface";
import { getMyCrewList, getCrewSearch } from "api/lib/crew";

const MyCrewList = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [MyCrewList, setMyCrewList] = useState<CrewInterface[]>([]);
  const [isSelectedType, setSelectedType] = useState("1");
  const [isSearchWord, setSearchWord] = useState("");

  useEffect(() => {
    getMyCrewList(
      accessToken,
      (res) => {
        console.log(res.data);
        setMyCrewList(res.data.resultBody);
        console.log("크루 목록조회 성공");
      },
      (err) => {
        const response = [
          {
            id: 1,
            name: "광주 싸피 크루",
            crewImage: "/test.png",
            region: "광주광역시 광산구",
            cntPeople: 12,
          },
          {
            id: 2,
            name: "SSAFY",
            crewImage: "/test.png",
            region: "광주광역시 광산구",
            cntPeople: 4,
          },
        ];
        setMyCrewList(response);
        console.log("크루 목록조회 실패", err);
      },
    );
  }, []);

  return (
    <div>
      {MyCrewList ? (
        <>
          {MyCrewList.map((crew, index) => (
            <CrewItem key={index} crew={crew} />
          ))}
        </>
      ) : (
        <div>검색결과가 존재하지 않습니다.</div>
      )}
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default MyCrewList;
