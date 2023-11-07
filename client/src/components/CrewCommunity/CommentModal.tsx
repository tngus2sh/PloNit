import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCommunityPage/CommentModal.module.css";
import CommentList from "./CommentList";
import { getCommentCreate } from "api/lib/feed";
import { FeedInterface } from "interface/crewInterface";

const CommentModal = ({ feed }: { feed: FeedInterface }) => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const profileImage = useSelector((state: any) => state.user.profileImg);
  const [isComment, setComment] = useState("");
  const onChangeComment = (event: any) => {
    setComment(event.target.value);
  };
  console.log(isComment);
  console.log(profileImage);
  const SendComment = () => {
    const data = {
      feedId: feed.id,
      content: isComment,
    };
    console.log(data);
    getCommentCreate(
      accessToken,
      data,
      (res) => {
        console.log("댓글 생성 성공");
      },
      (err) => {
        console.log("댓글 생성 실패", err);
      },
    );
  };
  return (
    <div className={style.comment_modal}>
      <CommentList feed={feed} />
      <div className={style.modal_bottom}>
        <img src={profileImage} alt="프로필" />
        <input
          type="text"
          id="comment"
          placeholder="댓글을 작성해주세요"
          value={isComment}
          onChange={onChangeComment}
        />
        <Icon
          icon="bi:arrow-up"
          style={{ width: "1.5rem", height: "1.5rem" }}
          className={style.upload_icon}
          onClick={SendComment}
        />
      </div>
      <div style={{ height: "3rem" }}></div>
    </div>
  );
};

export default CommentModal;
