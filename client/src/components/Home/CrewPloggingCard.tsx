import React from "react";
import style from "styles/css/HomePage/CrewPloggingCard.module.css";
import CommonButton from "components/common/CommonButton";
import { MyCrewpingInterface } from "interface/authInterface";
import { Swiper, SwiperSlide } from "swiper/react";
import Swal from "sweetalert2";
import "swiper/css";
import "swiper/css/free-mode";
import "swiper/css/pagination";

import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";
import * as Crewping from "store/crewping-slice";

function isPastTime(targetTime: string): boolean {
  // 주어진 문자열을 Date 객체로 변환
  const targetDate = new Date(targetTime);

  // 현재 한국 시간을 가져오기
  const koreaTime = new Date().toLocaleString("en-US", {
    timeZone: "Asia/Seoul",
  });
  const currentTime = new Date(koreaTime);

  // 주어진 시간이 현재 시간보다 이전인지 확인
  const isPast = targetDate < currentTime;

  return isPast;
}

const CrewPloggingCard = ({ card }: { card: MyCrewpingInterface }) => {
  console.log(card);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { id, isMaster, startDate } = card;
  const nickName = useSelector<rootState, string>((state) => {
    return state.user.info.nickname;
  });
  const weight = useSelector<rootState, number>((state) => {
    return state.user.info.weight;
  });
  const DDAY = card.dday === 0 ? "day" : card.dday;

  const onClick = () => {
    if (isPastTime(startDate)) {
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
    } else {
      Swal.fire({
        icon: "error",
        text: "아직 크루핑을 시작할 수 없습니다.",
      });
    }
  };

  return (
    <div className={style.plogging_card}>
      <div className={style.plogging_text}>
        <div className={style.crewping_leader}>{card.crewName}</div>
        <div className={style.crewping_name}>{card.crewpingName}</div>
        <div className={style.crewping_dday}>D - {DDAY}</div>
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
                <img src={image} alt="crewImage" />
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
        onClick={onClick}
      />
    </div>
  );
};

export default CrewPloggingCard;
