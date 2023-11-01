import React, { useState, useEffect } from "react";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCommunityPage/CommentModal.module.css";
import CommentList from "./CommentList";

interface CommentModalProps {
  setCommentModalOpen: (value: boolean) => void;
}

const CommentModal = ({ setCommentModalOpen }: CommentModalProps) => {
  const [isActive, setIsActive] = useState(false);

  useEffect(() => {
    setIsActive(true);
  }, []);

  return (
    <div className={`${style.comment_modal} ${isActive ? style.active : ""}`}>
      <div className={style.modal_top}>
        <div className={style.bar}></div>
        <div>댓글</div>
        <hr />
      </div>
      <CommentList />
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
