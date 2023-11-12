import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import "swiper/css/effect-cards";
import { Pagination, Navigation, EffectCards } from "swiper/modules";
import style from "styles/css/HomePage/Carousel.module.css";
import CrewPloggingCard from "./CrewPloggingCard";

interface Card {
  id: number;
  content: string;
}

interface CarouselProps {
  card_list: Card[];
}

const Carousel = ({ card_list }: CarouselProps) => {
  return (
    <Swiper
      effect={"cards"}
      grabCursor={true}
      modules={[EffectCards]}
      className="mySwiper"
    >
      {card_list.map((card, index) => (
        <SwiperSlide key={card.id} className={style.centerSlide}>
          <div className={`${style.card} card-${index}`}>
            <CrewPloggingCard />
          </div>
        </SwiperSlide>
      ))}
    </Swiper>
  );
};

export default Carousel;
