import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { BackTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
import style from "styles/css/FeedCreate.module.css";
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination } from "swiper/modules";
import { Icon } from "@iconify/react";
import { getFeedCreate } from "api/lib/feed";
import styled from "styled-components";

import "swiper/css";
import "swiper/css/pagination";

const StyledSwiper = styled(Swiper)`
  .swiper-pagination-bullet-active {
    background: #2cd261;
  }
`;

const FeedCreatePage = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const [isFeedIntroduce, setFeedIntroduce] = useState("");
  const [isFeedImages, setFeedImages] = useState<File[]>([]);
  const [previewUrls, setPreviewUrls] = useState<string[]>([]);
  const onChangeFeedIntroduce = (event: any) => {
    setFeedIntroduce(event.target.value);
  };

  const handleImageUpload = (event: any) => {
    const files = Array.from(event.target.files) as File[];
    setFeedImages([...isFeedImages, ...files]);
    const newUrls = files.map((file) => URL.createObjectURL(file));
    setPreviewUrls([...previewUrls, ...newUrls]);
  };

  const feedCreateHandler = () => {
    if (isFeedImages.length < 1) {
      alert("피드 이미지를 등록하세요");
      return;
    } else if (!isFeedIntroduce) {
      alert("피드 내용을 입력하세요");
      return;
    }
    const formData = new FormData();
    formData.append("content", isFeedIntroduce);
    if (crewId) {
      formData.append("crewId", crewId);
    }
    if (isFeedImages) {
      isFeedImages.slice(0, 5).forEach((image) => {
        formData.append("feedPictures", image);
      });
    }

    getFeedCreate(
      accessToken,
      formData,
      (res) => {
        console.log(res.data);
        console.log("피드 생성 성공");
        navigate(`/crew/community/${crewId}`);
      },
      (err) => {
        console.log("피드 생성 에러", err);
      },
    );
  };
  const handleDeleteImage = (id: any) => {
    setFeedImages(isFeedImages.filter((_, index) => index !== id));
    setPreviewUrls(previewUrls.filter((_, index) => index !== id));
  };

  return (
    <div>
      <BackTopBar text="피드 생성" />
      <StyledSwiper
        pagination={true}
        modules={[Pagination]}
        className={style.mySwiper}
      >
        {isFeedImages.length < 5 && (
          <SwiperSlide>
            <label className={style.photo} htmlFor="input_file">
              <Icon
                icon="bi:camera"
                style={{ width: "3rem", height: "2.5rem" }}
              />
              <div>사진을 등록해주세요</div>
            </label>
            <input
              type="file"
              multiple
              id="input_file"
              accept="image/*"
              style={{ display: "none" }}
              onChange={handleImageUpload}
            />
          </SwiperSlide>
        )}
        {previewUrls.slice(0, 5).map((url, id) => (
          <SwiperSlide
            key={id}
            style={{
              backgroundImage: `url(${url})`,
            }}
            className={style.feedimg}
          >
            <Icon
              icon="bi:x"
              className={style.icon}
              style={{ width: "3rem", height: "3rem", color: "white" }}
              onClick={() => handleDeleteImage(id)}
            />
          </SwiperSlide>
        ))}
      </StyledSwiper>
      <div className={style.introduce}>
        <textarea
          className={style.inputBox}
          name="feed_introduce"
          id="feed_introduce"
          placeholder="내용을 작성해주세요"
          value={isFeedIntroduce}
          onChange={onChangeFeedIntroduce}
        ></textarea>
      </div>
      <CommonButton
        text="피드 작성하기"
        styles={{
          backgroundColor: "#2cd261",
        }}
        onClick={feedCreateHandler}
      />
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default FeedCreatePage;
