import React, { useState } from "react";
import style from "styles/css/CrewList/CrewListItem.module.css";
import { Icon } from "@iconify/react";
import Input from "components/common/Input";
import { useNavigate } from "react-router-dom";

// CrewItem 컴포넌트의 props 타입
type CrewItemProps = {
  title: string;
  location: string;
  memberCount: string;
  imageUrl: string; // 이미지 URL 추가
};

const CrewItem: React.FC<CrewItemProps> = ({
  title,
  location,
  memberCount,
  imageUrl,
}) => {
  const navigate = useNavigate();
  const goCommunity = () => {
    navigate("/crew/community");
  };

  return (
    <div className={style.crewping_Item} onClick={goCommunity}>
      <div className={style.crewping_img}>
        <img src={imageUrl} alt="Crew Image" />
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

const TotalCrewList = () => {
  const [inputValue, setInputValue] = useState<string>("");

  // 핸들러 함수의 매개변수 타입 지정
  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  };

  const list = [
    {
      title: "하남동 플로깅 크루",
      location: "광산구 장덕동",
      memberCount: "인원수 25",
      imageUrl: "your-image-url-1.jpg",
    },
    {
      title: "다른 크루 이름",
      location: "다른 위치",
      memberCount: "멤버 20",
      imageUrl: "your-image-url-2.jpg",
    },
    {
      title: "다른 크루 이름",
      location: "다른 위치",
      memberCount: "멤버 20",
      imageUrl: "your-image-url-2.jpg",
    },
    // ... 추가적으로 더 많은 아이템들을 넣을 수 있습니다.
  ];

  return (
    <div>
      <Input
        id="crewping_name"
        type="text"
        value={inputValue}
        onChange={handleChange}
        placeholder="크루명, 지역으로 검색"
      />

      {list.map((crew, index) => (
        <CrewItem
          key={index}
          title={crew.title}
          location={crew.location}
          memberCount={crew.memberCount}
          imageUrl={crew.imageUrl}
        />
      ))}

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
    </div>
  );
};

export default TotalCrewList;
