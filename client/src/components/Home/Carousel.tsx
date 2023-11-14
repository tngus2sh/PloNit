import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import "swiper/css/effect-cards";
import { Pagination, Navigation, EffectCards } from "swiper/modules";
import style from "styles/css/HomePage/Carousel.module.css";
import CrewPloggingCard from "./CrewPloggingCard";
import styled from "styled-components";
import { getMyCrewping } from "api/lib/members";
import { MyCrewpingInterface } from "interface/authInterface";

const StyledSwiper = styled(Swiper)`
  .swiper-slide-shadow {
    background: none !important;
  }
`;
const SwiperContainer = styled.div`
  width: 100vw;
  overflow: hidden;
  @media (min-width: 501px) {
    width: 500px;
  }
`;
const Carousel = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isMyCrewpingList, setMyCrewpingList] = useState<MyCrewpingInterface[]>(
    [],
  );

  useEffect(() => {
    getMyCrewping(
      accessToken,
      (res) => {
        console.log("나의 크루핑 조회 성공");
        console.log(res.data);
        console.log(res.data.resultBody);
        setMyCrewpingList(res.data.resultBody);
      },
      (err) => {
        console.log("나의 크루핑 조회 실패", err);
      },
    );
  }, []);

  return (
    <SwiperContainer>
      <StyledSwiper
        effect={"cards"}
        grabCursor={true}
        modules={[EffectCards]}
        className="mySwiper"
      >
        {isMyCrewpingList.map((card, index) => (
          <SwiperSlide key={card.id} className={style.centerSlide}>
            <CrewPloggingCard card={card} />
          </SwiperSlide>
        ))}
      </StyledSwiper>
    </SwiperContainer>
  );
};

export default Carousel;
