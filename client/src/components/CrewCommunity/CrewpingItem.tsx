import React from "react";
import { useNavigate } from "react-router-dom";
import style from "styles/css/CrewCommunityPage/CrewpingItem.module.css";
import { Icon } from "@iconify/react";
import { CrewpingInterface } from "interface/crewInterface";

const formattedDateTime = (datestr: any) => {
  const days = ["일", "월", "화", "수", "목", "금", "토"];
  const date = new Date(datestr);

  const month = date.getMonth() + 1;
  const day = date.getDate();
  const dayName = days[date.getDay()];
  const hour = date.getHours();
  const minute = date.getMinutes();
  const formattedDate = `${month}월 ${day}일(${dayName}) ${hour}:${
    minute < 10 ? "0" : ""
  }${minute}`;

  return formattedDate;
};

const CrewpingItem = ({ crewping }: { crewping: CrewpingInterface }) => {
  const navigate = useNavigate();
  const goCrewpingDetailHandler = () => {
    // id가 선택적 프로퍼티이므로, 존재하는 경우에만 내비게이션을 수행합니다.
    // console.log("crewping:", crewping);
    // console.log("crewping id: ", crewping.crewpingId);
    // if (crewping && crewping.crewpingId) {
    // crewping과 crewping.id가 존재하는지 확인합니다.
    navigate(`/crew/crewping/detail/${crewping.crewpingId}`);
    // }
  };

  // cntPeople과 maxPeople이 숫자 또는 문자열일 수 있으므로, 적절히 표시합니다.
  // const participantsDisplay =
  //   crewping.cntPeople !== undefined && crewping.maxPeople !== undefined
  //     ? `${crewping.cntPeople}/${crewping.maxPeople}`
  //     : "참가자 정보 없음";

  return (
    <div className={style.crewping_Item} onClick={goCrewpingDetailHandler}>
      <div className={style.crewping_img}>
        {crewping.crewpingImage && (
          <img src={crewping.crewpingImage} alt={crewping.name} />
        )}
      </div>
      <div className={style.crewping_content}>
        <div className={style.crewping_title}>{crewping.name}</div>
        <div className={style.first}>
          <div className={style.date}>
            <Icon
              icon="bi:calendar-check"
              style={{
                width: "1.1rem",
                height: "1.1rem",
                marginRight: "0.3rem",
              }}
            />
            <div>{formattedDateTime(crewping.startDate)}</div>
          </div>
          <div className={style.people}>
            <Icon
              icon="bi:person-fill"
              className={style.people_icon}
              style={{
                width: "1.2 rem",
                height: "1.2rem",
                marginRight: "0.3rem",
              }}
            />
            {/* <div>{participantsDisplay}</div> */}
            <div>
              {crewping.cntPeople}/{crewping.maxPeople} 명
            </div>
          </div>
        </div>

        <div className={style.second}>
          <Icon
            icon="bi:geo-alt"
            className={style.people_icon}
            style={{
              width: "1.1rem",
              height: "1.2rem",
              marginRight: "0.2rem",
            }}
          />
          <div>{crewping.place}</div>
        </div>
      </div>
    </div>
  );
};

export default CrewpingItem;
