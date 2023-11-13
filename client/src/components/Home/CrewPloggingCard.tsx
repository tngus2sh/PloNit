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
        <div className={style.crewping_leader}>내가 대장</div>
        <div className={style.crewping_name}>장덕동 지킴이</div>
        <div className={style.crewping_dday}>D - 3</div>
        <div className={style.crewping_date}>10.27(금) 17:00~18:30</div>
        <div className={style.crewping_place}>수완호수공원</div>
        <div className={style.crewping_member}>크루핑 멤버 5</div>
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
            <SwiperSlide
              style={{ width: "unset", zIndex: 1 }}
              className={style.participant_item}
            >
              <img src="/metamong.png" alt="몽" />
            </SwiperSlide>
            <SwiperSlide
              style={{ width: "unset", zIndex: 1 }}
              className={style.participant_item}
            >
              <img src="/metamong.png" alt="몽" />
            </SwiperSlide>
            <SwiperSlide
              style={{ width: "unset", zIndex: 1 }}
              className={style.participant_item}
            >
              <img src="/metamong.png" alt="몽" />
            </SwiperSlide>
            <SwiperSlide
              style={{ width: "unset", zIndex: 1 }}
              className={style.participant_item}
            >
              <img src="/metamong.png" alt="몽" />
            </SwiperSlide>
            <SwiperSlide
              style={{ width: "unset", zIndex: 1 }}
              className={style.participant_item}
            >
              <img src="/metamong.png" alt="몽" />
            </SwiperSlide>
            <SwiperSlide
              style={{ width: "unset", zIndex: 1 }}
              className={style.participant_item}
            >
              <img src="/metamong.png" alt="몽" />
            </SwiperSlide>
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
