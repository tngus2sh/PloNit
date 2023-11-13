import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import "swiper/css/effect-cards";
import { Pagination, Navigation, EffectCards } from "swiper/modules";
import style from "styles/css/HomePage/Carousel.module.css";
import CrewPloggingCard from "./CrewPloggingCard";
import styled from "styled-components";

interface Card {
  id: number;
  content: string;
}

interface CarouselProps {
  card_list: Card[];
}
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
const Carousel = ({ card_list }: CarouselProps) => {
  return (
    <SwiperContainer>
      <StyledSwiper
        effect={"cards"}
        grabCursor={true}
        modules={[EffectCards]}
        className="mySwiper"
      >
        {card_list.map((card, index) => (
          <SwiperSlide key={card.id} className={style.centerSlide}>
            <CrewPloggingCard />
          </SwiperSlide>
        ))}
      </StyledSwiper>
    </SwiperContainer>
  );
};

export default Carousel;
