import React from "react";
import style from "styles/css/MyBadgePage/MissionBadge.module.css";

const MissionBadge = () => {
  const images = [
    { src: "/metamong.png", alt: "몽" },
    { src: "/metamong.png", alt: "몽" },
    { src: "/metamong.png", alt: "몽" },
    { src: "/metamong.png", alt: "몽" },
    { src: "/metamong.png", alt: "몽" },
    { src: "/metamong.png", alt: "몽" },
    { src: "/metamong.png", alt: "몽" },
  ];

  const renderImages = () => {
    return images.map((image, index) => (
      <div className={style.imageContainer} key={index}>
        <img src={image.src} alt={image.alt} />
      </div>
    ));
  };

  return <div className={style.missionBadge}>{renderImages()}</div>;
};

export default MissionBadge;
