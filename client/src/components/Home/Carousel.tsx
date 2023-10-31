import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";
import { Pagination, Navigation } from "swiper/modules";
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
      slidesPerView={1.2}
      centeredSlides={true}
      spaceBetween={20}
      modules={[Pagination, Navigation]}
      className={style.mySwiper}
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
