import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { userActions } from "store/user-slice";
import { Icon } from "@iconify/react";
import CrewCommunityInfo from "components/CrewCommunity/CrewCommunityInfo";
import Notice from "components/CrewCommunity/Notice";
import CustomizedTabs from "components/common/CustomTab";
import CrewFeed from "components/CrewCommunity/CrewFeed";
import CrewpingList from "components/CrewCommunity/CrewpingList";
import CommonButton from "components/common/CommonButton";
import style from "styles/css/CrewCommunityPage.module.css";
import { getCrewDetail, getCrewRegister } from "api/lib/crew";
import { CrewInterface } from "interface/crewInterface";
import { OkModal } from "components/common/AlertModals";

const CrewCommunityPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const User = useSelector((state: any) => state.user);
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
  const goNoticeHandler = () => {
    document.body.style.overflow = "scroll";
    navigate(`/crew/community/notice/${crewId}`);
  };

  const JoinCrew = () => {
    getCrewRegister(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("크루 가입 요청 성공");
        console.log(res.data.resultBody);
        // alert("크루 가입 요청을 보냈습니다.");
        OkModal({ text: "크루 가입 요청을 보냈습니다." });
        fetchCrewDetail();
      },
      (err) => {
        console.log("크루 가입 요청 실패", err);
      },
    );
  };

  useEffect(() => {
    fetchCrewDetail();
  }, []);

  const fetchCrewDetail = () => {
    getCrewDetail(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("크루 상세 조회 성공");
        console.log(res.data);
        setCrewDetail(res.data.resultBody);
        const data = {
          isMyCrew: res.data.resultBody.isMyCrew,
          isCrewMaster: res.data.resultBody.isCrewMaster,
          isCrewpingMaster: User.crewinfo.isCrewpingMaster,
        };
        console.log(data);
        dispatch(userActions.myCrewHandler(data));
      },
      (err) => {
        console.log("크루 상세 조회 실패", err);
      },
    );
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
      <CrewCommunityInfo
        crew={isCrewDetail}
        master={isCrewDetail.isCrewMaster || false}
      />
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
                    width: "3rem",
                    height: "3rem",
                    padding: "0.7rem",
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
                    width: "3rem",
                    height: "3rem",
                    padding: "0.7rem",
                  }}
                />
              </div>
            </div>
            {isCrewDetail.isCrewMaster && (
              <div className={style.create} style={{ bottom: "14.5rem" }}>
                <div className={style.title}>공지사항</div>
                <div className={style.open_button} onClick={goNoticeHandler}>
                  <Icon
                    className={style.pencil}
                    icon="bi:pencil"
                    style={{
                      width: "3rem",
                      height: "3rem",
                      padding: "0.7rem",
                    }}
                  />
                </div>
              </div>
            )}
          </div>
        </>
      )}
      {isCrewDetail.isMyCrew ? (
        <Icon
          className={style.plus}
          onClick={toggleButton}
          icon="bi:plus-lg"
          style={{
            width: "2rem",
            height: "2rem",
            color: "white",
            marginTop: "0.25rem",
            padding: "0.6rem",
          }}
        />
      ) : (
        <>
          <div className={style.register_btn}>
            {isCrewDetail.isWaiting ? (
              <CommonButton
                text="승인 대기"
                styles={{
                  backgroundColor: "#999999",
                }}
              />
            ) : (
              <CommonButton
                text="크루 가입"
                styles={{
                  backgroundColor: "#2cd261",
                }}
                onClick={JoinCrew}
              />
            )}
          </div>
          <div style={{ height: "4rem" }}></div>
        </>
      )}
    </div>
  );
};

export default CrewCommunityPage;
