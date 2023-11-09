import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import style from "styles/css/CrewList/TotalCrewList.module.css";
import CrewItem from "./CrewItem";
import { CrewInterface } from "interface/crewInterface";
import { getCrewList, getCrewSearch } from "api/lib/crew";

const TotalCrewList = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [totalCrewList, setTotalCrewList] = useState<CrewInterface[]>([]);
  const [isSelectedType, setSelectedType] = useState("1");
  const [isSearchWord, setSearchWord] = useState("");

  const handleSearchInputChange = (event: any) => {
    setSearchWord(event.target.value);
    getCrewSearch(
      accessToken,
      Number(isSelectedType),
      isSearchWord,
      (res) => {
        console.log(res.data);
        setTotalCrewList(res.data.resultBody);
        console.error("크루 검색 성공");
      },
      (err) => {
        console.error("크루 검색 실패", err);
      },
    );
  };

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
      <div className={style.search}>
        <select
          className={style.select}
          value={isSelectedType}
          onChange={(e) => setSelectedType(e.target.value)}
        >
          <option value="1" key="name">
            이름
          </option>
          <option value="2" key="region">
            지역
          </option>
        </select>
        <input
          type="text"
          name="search"
          id="search"
          className={style.inputBox}
          value={isSearchWord}
          onChange={handleSearchInputChange}
        />
      </div>
      {totalCrewList.map((crew, index) => (
        <CrewItem key={index} crew={crew} />
      ))}
    </div>
  );
};

export default TotalCrewList;
