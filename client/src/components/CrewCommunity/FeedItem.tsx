import React from "react";
import { Icon } from "@iconify/react";
import style from "styles/css/FeedItem.module.css";

const FeedItem = () => {
  const feedImageStyle = {
    backgroundImage: `url("/feed_img.png")`,
  };

  return (
    <div className={style.feed_item}>
      <div className={style.name_area}>
        <img src="/metamong.png" alt="몽" />
        <div className={style.nickname}>메타몽</div>
      </div>
      <div className={style.feed_img} style={feedImageStyle}></div>
      <div className={style.icon_area}>
        <Icon icon="bi:heart" style={{ width: "1.8rem", height: "1.8rem" }} />
        <Icon
          icon="bi:chat-left"
          style={{ width: "1.8rem", height: "1.8rem", marginLeft: "0.5rem" }}
        />
      </div>
      <div className={style.like_count}>좋아요 112개</div>
      <div className={style.content_area}>
        <div>메타몽</div>
        <div>플로깅 참여 완료!</div>
      </div>
      <div className={style.comment_area}>
        <div className={style.comment_count}>댓글 10개 모두 보기</div>
        <div className={style.comment_content}>
          <div>HAMSTER</div>
          <div>나도 가고 싶다</div>
        </div>
      </div>
      <div className={style.date}>10월 15일</div>
    </div>
  );
};

export default FeedItem;
