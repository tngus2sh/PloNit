import React, { useState } from "react";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCommunityPage/FeedItem.module.css";
import CommentModal from "components/CrewCommunity/CommentModal";
import { FeedInterface } from "interface/crewInterface";
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination } from "swiper/modules";
import styled from "styled-components";

import "swiper/css";
import "swiper/css/pagination";

const StyledSwiper = styled(Swiper)`
  .swiper-pagination-bullet-active {
    background: #2cd261;
  }
  .swiper-pagination {
    margin-top: 1rem;
    position: relative;
  }
`;

// const feedPictures = [
//   { feedPicture: "/feed_img.png" },
//   { feedPicture: "/metamong.png" },
//   { feedPicture: "/feed_img.png" },
//   { feedPicture: "/metamong.png" },
//   { feedPicture: "/feed_img.png" },
// ];

const FeedItem = ({ feed }: { feed: FeedInterface }) => {
  const [isCommentModalOpen, setCommentModalOpen] = useState(false);
  const isfeedImages = feed.feedPictures;
  const toggleCommentModal = () => {
    setCommentModalOpen(!isCommentModalOpen);
    if (isCommentModalOpen) {
      document.body.style.overflow = "scroll";
    } else {
      document.body.style.overflow = "hidden";
    }
  };

  return (
    <div className={style.feed_item}>
      <div className={style.name_area}>
        <img src={feed.profileImage} alt="피드" />
        <div className={style.nickname}>{feed.nickname}</div>
      </div>

      <StyledSwiper
        pagination={true}
        modules={[Pagination]}
        className={style.mySwiper}
      >
        {isfeedImages.map((item: { feedPicture: string }, id: number) => (
          <SwiperSlide key={id}>
            <div
              className={style.feed_img}
              style={{ backgroundImage: `url(${item.feedPicture})` }}
            ></div>
          </SwiperSlide>
        ))}
      </StyledSwiper>

      <div className={style.icon_area}>
        <Icon icon="bi:heart" style={{ width: "1.8rem", height: "1.8rem" }} />
        <Icon
          icon="bi:chat-left"
          onClick={toggleCommentModal}
          style={{ width: "2rem", height: "1.8rem", marginLeft: "1rem" }}
        />
      </div>
      <div className={style.like_count}>좋아요 {feed.likeCount}개</div>
      <div className={style.content_area}>
        <div>{feed.nickname}</div>
        <div>{feed.content}</div>
      </div>
      <div className={style.comment_area}>
        <div className={style.comment_count}>댓글 10개 모두 보기</div>
        <div className={style.comment_content}>
          <div>HAMSTER</div>
          <div>나도 가고 싶다</div>
        </div>
      </div>
      <div className={style.date}>10월 15일</div>
      {isCommentModalOpen && (
        <>
          <div className={style.modalbackground}></div>
          <CommentModal setCommentModalOpen={setCommentModalOpen} />
        </>
      )}
    </div>
  );
};

export default FeedItem;
