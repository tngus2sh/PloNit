import React from "react";
import style from "styles/css/HomePage/CrewPloggingCard.module.css";
import CommonButton from "components/common/CommonButton";
import { MyCrewpingInterface } from "interface/authInterface";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/free-mode";
import "swiper/css/pagination";

const CrewPloggingCard = ({ card }: { card: MyCrewpingInterface }) => {
  console.log(card);
  return (
    <div className={style.plogging_card}>
      <div className={style.plogging_text}>
        <div className={style.crewping_leader}>{card.crewName}</div>
        <div className={style.crewping_name}>{card.crewpingName}</div>
        <div className={style.crewping_dday}>D - {card.dday}</div>
        <div className={style.crewping_date}>{card.startDate}</div>
        <div className={style.crewping_place}>{card.place}</div>
        <div className={style.crewping_member}>
          크루핑 멤버 {card.cntPeople}
        </div>
        <div className={style.member_img}>
          <Swiper
            {...{
              slidesPerView: "auto",
              spaceBetween: 5,
              freeMode: true,
              pagination: {
                clickable: true,
              },
            }}
          >
            {card.memberProfileImage.map((image: string, index: number) => (
              <SwiperSlide key={index} style={{ width: "unset", zIndex: 1 }}>
                <img src={image} alt="몽" />
              </SwiperSlide>
            ))}
          </Swiper>
        </div>
      </div>
      <CommonButton
        text="시작하기"
        styles={{
          backgroundColor: "#2cd261",
        }}
      />
    </div>
  );
};

export default CrewPloggingCard;
