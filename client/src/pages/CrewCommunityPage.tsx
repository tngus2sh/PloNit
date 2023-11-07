import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { Icon } from "@iconify/react";
import CrewCommunityInfo from "components/CrewCommunity/CrewCommunityInfo";
import Notice from "components/CrewCommunity/Notice";
import CustomizedTabs from "components/common/CustomTab";
import CrewFeed from "components/CrewCommunity/CrewFeed";
import CrewpingList from "components/CrewCommunity/CrewpingList";
import style from "styles/css/CrewCommunityPage.module.css";
import { getCrewDetail } from "api/lib/crew";
import { CrewInterface } from "interface/crewInterface";

const CrewCommunityPage = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const [isVisibleButton, setVisibleButton] = useState(false);
  const [isCrewDetail, setCrewDetail] = useState<CrewInterface>(
    {} as CrewInterface,
  );

  const goBackHandler = () => {
    navigate(-1);
  };
  const goFeedHandler = () => {
    document.body.style.overflow = "scroll";
    navigate(`/feed/create/${crewId}`);
  };
  const goCrewpingCreateHandler = () => {
    document.body.style.overflow = "scroll";
    navigate(`/crew/crewping/create/${crewId}`);
  };

  useEffect(() => {
    getCrewDetail(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("크루 상세 조회 성공");
        console.log(res.data.resultBody);
        setCrewDetail(res.data.resultBody);
      },
      (err) => {
        console.log("크루 상세 조회 실패", err);
      },
    );
  }, []);

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
      <CrewCommunityInfo crew={isCrewDetail} />
      <div className={style.divide}></div>
      <Notice crew={isCrewDetail} />
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
