import React from "react";
import style from "styles/css/HomePage/CrewPloggingCard.module.css";
import CommonButton from "components/common/CommonButton";
import { MyCrewpingInterface } from "interface/authInterface";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/free-mode";
import "swiper/css/pagination";

import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";
import * as Crewping from "store/crewping-slice";

const CrewPloggingCard = ({ card }: { card: MyCrewpingInterface }) => {
  console.log(card);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { id, isMaster } = card;
  const nickName = useSelector<rootState, string>((state) => {
    return state.user.info.nickname;
  });
  const weight = useSelector<rootState, number>((state) => {
    return state.user.info.weight;
  });

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
        onClick={() => {
          dispatch(P.clear());
          dispatch(Crewping.clear());
          dispatch(P.setCrewpingId(id));
          dispatch(Crewping.setRoomId(`${id}`));
          dispatch(Crewping.setCharge(isMaster));
          dispatch(Crewping.setSenderId(nickName));
          dispatch(P.setBeforeCrewping(true));
          if (weight > 0) {
            dispatch(P.setKg(weight));
          }
          navigate("/plogging");
        }}
      />
    </div>
  );
};

export default CrewPloggingCard;
