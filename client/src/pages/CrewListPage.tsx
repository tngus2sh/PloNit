import React from "react";
import { BasicTopBar } from "components/common/TopBar";
import CustomTab from "components/common/CustomTab";
import MyCrewList from "components/CrewList/MyCrewList";
import TotalCrewList from "components/CrewList/TotalCrewList";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewListPage.module.css";
import { useNavigate } from "react-router-dom";

type IconProps = {
  onClick: (event: React.MouseEvent<SVGSVGElement>) => void;
};

const CrewListPage = () => {
  const navigate = useNavigate();

  const tabProps = {
    나의크루목록: <MyCrewList />,
    전체크루목록: <TotalCrewList />,
  };

  const goCrewcreate = () => {
    navigate("/crew/create");
  };

  return (
    <div>
      <BasicTopBar text="크루 목록" />
      <CustomTab tabProps={tabProps} />

      <Icon
        className={style.plus}
        onClick={goCrewcreate}
        icon="bi:plus-lg"
        style={{
          width: "2.5rem",
          height: "2.5rem",
          color: "white",
          marginTop: "0.25rem",
        }}
      />
    </div>
  );
};

export default CrewListPage;
