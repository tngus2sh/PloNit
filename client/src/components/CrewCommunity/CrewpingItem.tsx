import React from "react";
import { useNavigate } from "react-router-dom";
import style from "styles/css/CrewCommunityPage/CrewpingItem.module.css";
import RoomIcon from "@mui/icons-material/Room";
import PersonIcon from "@mui/icons-material/Person";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import { CrewpingInterface } from "interface/crewInterface"; // 경로는 실제 경로에 맞게 조정해주세요.

const CrewpingItem = ({ crewping }: { crewping: CrewpingInterface }) => {
  const navigate = useNavigate();
  const goCrewpingDetailHandler = () => {
    // id가 선택적 프로퍼티이므로, 존재하는 경우에만 내비게이션을 수행합니다.
    console.log("crewping:", crewping);
    console.log("crewping id: ", crewping.crewpingId);
    if (crewping && crewping.crewpingId) {
      // crewping과 crewping.id가 존재하는지 확인합니다.
      navigate(`/crew/crewping/detail/${crewping.crewpingId}`, {
        state: { crewping: crewping },
      });
    }
  };

  // cntPeople과 maxPeople이 숫자 또는 문자열일 수 있으므로, 적절히 표시합니다.
  const participantsDisplay =
    crewping.cntPeople !== undefined && crewping.maxPeople !== undefined
      ? `${crewping.cntPeople}/${crewping.maxPeople}`
      : "참가자 정보 없음";

  return (
    <div className={style.crewping_Item} onClick={goCrewpingDetailHandler}>
      <div className={style.crewping_img}>
        {/* crewpingImage가 선택적 프로퍼티이므로 존재할 때만 이미지를 표시합니다. */}
        {crewping.crewpingImage && (
          <img src={crewping.crewpingImage} alt={crewping.name} />
        )}
      </div>
      <div className={style.crewping_content}>
        <div className={style.crewping_title}>{crewping.name}</div>
        <div className={style.first}>
          <div className={style.place}>
            <RoomIcon
              className={style.people_icon}
              style={{ width: "1.2rem", height: "1.2rem" }}
            />
            <div>{crewping.place}</div>
          </div>
          <div className={style.people}>
            <PersonIcon
              className={style.people_icon}
              style={{ width: "1.2rem", height: "1.2rem" }}
            />
            <div>{participantsDisplay}</div>
          </div>
        </div>
        {crewping.startDate && (
          <div className={style.second}>
            <CalendarMonthIcon style={{ width: "1.2rem", height: "1.2rem" }} />
            {/* startDate가 존재하면 표시하고, 아니면 '날짜 정보 없음'을 표시합니다. */}
            <div>{crewping.startDate || "날짜 정보 없음"}</div>
          </div>
        )}
      </div>
    </div>
  );
};

export default CrewpingItem;
