import React, { useState } from "react";
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
  const [isVisibleButton, setVisibleButton] = useState(false);

  const goBackHandler = () => {
    navigate(-1);
  };
  const goFeedHandler = () => {
    document.body.style.overflow = "scroll";
    navigate("/feed/create");
  };
  const goCrewpingCreateHandler = () => {
    document.body.style.overflow = "scroll";
    navigate("/crew/crewping/create");
  };

  const tabProps = {
    피드: <CrewFeed />,
    크루핑: <CrewpingList />,
  };

  const toggleButton = () => {
    setVisibleButton(!isVisibleButton);
    if (isVisibleButton) {
      document.body.style.overflow = "scroll";
    } else {
      document.body.style.overflow = "hidden";
    }
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

      {isVisibleButton && (
        <>
          <div className={style.modalbackground}>
            <div className={style.create} style={{ bottom: "7.5rem" }}>
              <div className={style.title}>피드 작성</div>
              <div className={style.open_button} onClick={goFeedHandler}>
                <Icon
                  icon="bi:pencil"
                  style={{
                    width: "2rem",
                    height: "2rem",
                    marginTop: "0.5rem",
                  }}
                />
              </div>
            </div>
            <div className={style.create} style={{ bottom: "11rem" }}>
              <div className={style.title}>크루핑 생성</div>
              <div
                className={style.open_button}
                onClick={goCrewpingCreateHandler}
              >
                <Icon
                  icon="bi:pencil"
                  style={{
                    width: "2rem",
                    height: "2rem",
                    marginTop: "0.5rem",
                  }}
                />
              </div>
            </div>
          </div>
        </>
      )}

      <div className={style.plus} onClick={toggleButton}>
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
    </div>
  );
};

export default CrewCommunityPage;
