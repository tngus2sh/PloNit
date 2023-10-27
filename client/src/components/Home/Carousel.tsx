import React, { useState, useRef } from "react";
import style from "styles/css/HomePage/Carousel.module.css";
import CrewPloggingCard from "./CrewPloggingCard";

interface Card {
  id: number;
  content: string;
}

interface CarouselProps {
  card_list: Card[];
}
const cardWidthPercentage = 21; // 카드 너비의 백분율
const screenWidth = window.innerWidth;

const Carousel = ({ card_list }: CarouselProps) => {
  const [currentCardIndex, setCurrentCardIndex] = useState(0);
  const containerRef = useRef<HTMLDivElement | null>(null);
  let startX: number | null = null;

  const handleTouchStart = (e: React.TouchEvent) => {
    startX = e.touches[0].clientX;
  };

  const handleTouchEnd = (e: React.TouchEvent) => {
    if (startX === null) return;

    const endX = e.changedTouches[0].clientX;
    const deltaX = endX - startX;

    if (deltaX > 50) {
      if (currentCardIndex > 0) {
        setCurrentCardIndex((prevIndex) => prevIndex - 1);
      }
    } else if (deltaX < -50) {
      if (currentCardIndex < card_list.length - 1) {
        setCurrentCardIndex((prevIndex) => prevIndex + 1);
      }
    }

    startX = null;
  };

  return (
    <div
      className={style.carousel}
      onTouchStart={handleTouchStart}
      onTouchEnd={handleTouchEnd}
      ref={containerRef}
    >
      {card_list.map((card, index) => (
        <div
          key={card.id}
          className={`${style.card} ${
            index === currentCardIndex ? style.activeCard : ""
          }`}
          style={{
            transform: `translateX(${
              (index - currentCardIndex) *
              cardWidthPercentage *
              0.01 *
              screenWidth
            }px)`,
            transition: "transform 0.5s ease-in-out",
          }}
        >
          <CrewPloggingCard />
        </div>
      ))}
    </div>
  );
};

export default Carousel;
