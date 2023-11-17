import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "styles/css/PloggingPage/PloggingSwiper.css";

const SwiperImage = ({ image, alt }: { image: string; alt: string }) => {
  return (
    <img
      src={image}
      alt={alt}
      style={{ width: "100%", aspectRatio: "1/1", borderRadius: "2.5%" }}
    />
  );
};

const PlusImage = () => {
  return (
    <div
      style={{
        width: "100%",
        aspectRatio: "1/1",
        borderRadius: "2.5%",
        backgroundColor: "#D9D9D9",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <div
        style={{
          height: "50%",
          width: "50%",
          backgroundSize: "contain",
          backgroundRepeat: "no-repeat",
          backgroundPosition: "center",
          backgroundImage: `url("/images/PloggingPage/Plus-Button.svg")`,
        }}
      ></div>
    </div>
  );
};

const PloggingSwiper = ({
  images,
  onClick,
}: {
  images: string[];
  onClick: () => void;
}) => {
  return (
    <div>
      <Swiper
        slidesPerView={2.5}
        freeMode={true}
        resistance={false}
        slideToClickedSlide={true}
        spaceBetween={10}
        pagination={{ clickable: true }}
        grabCursor={true}
      >
        {images.map((image, index) => {
          return (
            <SwiperSlide key={`plogging-image-${index}`}>
              <SwiperImage image={image} alt={`plogging-image-${index}`} />
            </SwiperSlide>
          );
        })}
        <SwiperSlide onClick={onClick}>
          <PlusImage />
        </SwiperSlide>
      </Swiper>
    </div>
  );
};

export default PloggingSwiper;
