import React, { useState } from "react";
import style from "styles/css/CrewList/CrewListItem.module.css";
import { Icon } from "@iconify/react";
import Input from "components/common/Input";

// CrewItem 컴포넌트의 props 타입
type MemberListProps = {
  title: string;
  location?: string;
  memberCount?: string;
  imageUrl: string; // 이미지 URL 추가
  showApproveButton?: boolean; // 승인 버튼 표시 여부를 위한 prop
  onApprove?: () => void; // 승인 버튼 클릭 시 호출할 함수
};

const MemberList: React.FC<MemberListProps> = ({
  title,
  location,
  memberCount,
  imageUrl,
  showApproveButton = false,
  onApprove,
}) => {
  return (
    <div className={style.crewping_Item}>
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
      {showApproveButton && (
        <React.Fragment>
          <button>승인</button>
          <button>거부</button>
        </React.Fragment>
      )}
    </div>
  );
};

const TotalCrewList = () => {
  const [inputValue, setInputValue] = useState<string>("");

  const list = [
    {
      title: "하남동 플로깅 크루",
      imageUrl: "your-image-url-1.jpg",
    },
    {
      title: "다른 크루 이름",
      imageUrl: "your-image-url-2.jpg",
    },
    {
      title: "다른 크루 이름",
      imageUrl: "your-image-url-2.jpg",
    },
    // ... 추가적으로 더 많은 아이템들을 넣을 수 있습니다.
  ];

  return (
    <div>
      {list.map((crew, index) => (
        <MemberList key={index} title={crew.title} imageUrl={crew.imageUrl} />
      ))}
    </div>
  );
};

export default TotalCrewList;
