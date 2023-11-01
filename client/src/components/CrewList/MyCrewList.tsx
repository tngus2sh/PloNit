import React from "react";
import style from "styles/css/CrewList/CrewListItem.module.css";

type CrewItemProps = {
  title: string;
  location: string;
  memberCount: string;
};

const CrewItem: React.FC<CrewItemProps> = ({
  title,
  location,
  memberCount,
}) => {
  return (
    <div className={style.crewping_Item}>
      <div className={style.crewping_img}>
        <img src="your-image-url-1.jpg" alt="Crew Image" />
      </div>
      <div className={style.crewping_content}>
        <div className={style.crewping_title}>{title}</div>
        <div className={style.first}>
          <div className={style.place}>{location}</div>
          <div className={style.people}>{memberCount}</div>
        </div>
      </div>
    </div>
  );
};

const MyCrewList = () => {
  return (
    <div>
      <CrewItem
        title="하남동 플로깅 크루"
        location="광산구 장덕동"
        memberCount="인원수 25"
      />
      <CrewItem
        title="하남동 플로깅 크루"
        location="광산구 장덕동"
        memberCount="멤버 25"
      />
    </div>
  );
};

export default MyCrewList;
