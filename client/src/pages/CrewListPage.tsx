import React from "react";
import { useNavigate } from "react-router-dom";
import { BasicTopBar } from "components/common/TopBar";
import CustomTab from "components/common/CustomTab";
import MyCrewList from "components/CrewList/MyCrewList";
import TotalCrewList from "components/CrewList/TotalCrewList";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewListPage.module.css";

const CrewListPage = () => {
  const navigate = useNavigate();

  const tabProps = {
    "나의 크루 목록": <MyCrewList />,
    "전체 크루 목록": <TotalCrewList />,
  };

  const goCrewcreate = () => {
    navigate("/crew/create");
  };

  return (
    <div style={{ height: "100%" }}>
      <BasicTopBar text="크루 목록" />
      <CustomTab tabProps={tabProps} />

      <Icon
        className={style.plus}
        onClick={goCrewcreate}
        icon="bi:plus-lg"
        style={{
          width: "2rem",
          height: "2rem",
          color: "white",
          marginTop: "0.25rem",
          padding: "0.6rem",
        }}
      />
    </div>
  );
};

export default CrewListPage;
