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

      <div>
        <h1>나 여깄어.</h1>
      </div>

      <div className={style.plus}>
        <Icon
          icon="bi:plus-lg"
          style={{
            width: "2.5rem",
            height: "2.5rem",
            color: "white",
            marginTop: "0.25rem",
          }}
        />
      </div>
      <div>
        <button onClick={goCrewcreate}>button</button>
      </div>
    </div>
  );
};

export default CrewListPage;
