import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCommunityPage/FeedItem.module.css";
import CommentModal from "components/CrewCommunity/CommentModal";
import { FeedInterface } from "interface/crewInterface";
import { Swiper, SwiperSlide } from "swiper/react";
import { Pagination } from "swiper/modules";
import styled from "styled-components";
import { getFeedDelete } from "api/lib/feed";
import Sheet from "react-modal-sheet";

import "swiper/css";
import "swiper/css/pagination";

const StyledSwiper = styled(Swiper)`
  .swiper-pagination-bullet-active {
    background: #2cd261;
  }
  .swiper-pagination {
    margin-top: 0.7rem;
    position: relative;
    z-index: 0;
  }
`;
const CustomSheet = styled(Sheet)`
  .react-modal-sheet-backdrop {
    @media (min-width: 500px) {
      width: 500px !important;
      left: calc((100% - 500px) / 2) !important;
    }
  }
  .react-modal-sheet-container {
    @media (min-width: 500px) {
      width: 500px !important;
      left: calc((100% - 500px) / 2) !important;
    }
  }
`;

const FeedItem = ({
  feed,
  fetchFeedList,
}: {
  feed: FeedInterface;
  fetchFeedList: () => void;
}) => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [isCommentModalOpen, setCommentModalOpen] = useState(false);
  const isfeedImages = feed.feedPictures;
  const toggleCommentModal = () => {
    setCommentModalOpen(!isCommentModalOpen);
  };

  const handleDeleteFeed = () => {
    getFeedDelete(
      accessToken,
      feed.id,
      (res) => {
        console.log(res.data);
        console.log("피드 삭제 성공");
        fetchFeedList();
      },
      (err) => {
        console.log("피드 삭제 에러", err);
      },
    );
  };

  return (
    <div className={style.feed_item}>
      <div className={style.name_area}>
        <div className={style.left}>
          <img src={feed.profileImage} alt="프로필" />
          <div className={style.nickname}>{feed.nickname}</div>
        </div>
        <div className={style.right}>
          {feed.isMine ? (
            <Icon
              icon="bi:trash"
              style={{ width: "1.5rem", height: "1.5rem" }}
              onClick={handleDeleteFeed}
            />
          ) : null}
        </div>
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
        {feed.comments.length ? (
          <div className={style.comment_count} onClick={toggleCommentModal}>
            댓글 {feed.comments.length}개 모두 보기
          </div>
        ) : null}
        {/* <div className={style.comment_content}>
          <div>HAMSTER</div>
          <div>나도 가고 싶다</div>
        </div> */}
      </div>
      <div className={style.date}>{feed.createdDate}</div>
      {isCommentModalOpen && (
        <>
          <CustomSheet
            isOpen={isCommentModalOpen}
            onClose={() => setCommentModalOpen(false)}
            tweenConfig={{ ease: "easeOut", duration: 0.3 }}
          >
            <Sheet.Container>
              <Sheet.Header />
              <Sheet.Content>
                <CommentModal feed={feed} fetchFeedList={fetchFeedList} />
              </Sheet.Content>
            </Sheet.Container>
            <Sheet.Backdrop />
          </CustomSheet>
        </>
      )}
    </div>
  );
};

export default FeedItem;
