import React from "react";
import { useNavigate } from "react-router-dom";
import { Icon } from "@iconify/react";
import CrewCommunityInfo from "components/CrewCommunity/CrewCommunityInfo";
import Notice from "components/CrewCommunity/Notice";
import CustomizedTabs from "components/common/CustomTab";
import CrewFeed from "components/CrewCommunity/CrewFeed";
import CrewpingList from "components/CrewCommunity/CrewpingList";
import style from "styles/css/CrewCommunityPage.module.css";

const CrewCommunityPage = () => {
  const navigate = useNavigate();

  const goBackHandler = () => {
    navigate(-1);
  };

  const tabProps = {
    피드: <CrewFeed />,
    크루핑: <CrewpingList />,
  };

  return (
    <div className={style.community_page}>
      <Icon
        icon="bi:chevron-left"
        onClick={goBackHandler}
        style={{
          width: "1.8rem",
          height: "1.8rem",
          color: "white",
        }}
        className={style.back_Icon}
      />
      <CrewCommunityInfo />
      <div className={style.divide}></div>
      <Notice />
      <div className={style.divide}></div>
      <CustomizedTabs tabProps={tabProps} />
    </div>
  );
};

export default CrewCommunityPage;
