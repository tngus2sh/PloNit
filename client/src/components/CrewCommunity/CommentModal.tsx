import React from "react";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCommunityPage/CommentModal.module.css";
import CommentItem from "./CommentItem";

const CommentModal = () => {
  return (
    <div className={style.comment_modal}>
      <div className={style.modal_top}>
        댓글
        <hr />
      </div>
      <CommentItem />
      <CommentItem />
      <CommentItem />
      <CommentItem />
      <div className={style.modal_bottom} contentEditable="true">
        <img src="/metamong.png" alt="몽" />
        <input type="text" name="" id="" placeholder="댓글을 작성해주세요" />
        <Icon
          icon="bi:arrow-up"
          style={{ width: "1.5rem", height: "1.5rem" }}
          className={style.upload_icon}
        />
      </div>
    </div>
  );
};

export default CommentModal;
