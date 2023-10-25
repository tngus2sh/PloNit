import React from "react";
import style from "styles/css/CrewCommunityPage/CommentModal.module.css";

const CommentItem = () => {
  return (
    <div className={style.comment_item}>
      <div className={style.comment_img}>
        <img src="/metamong.png" alt="몽" />
      </div>
      <div className={style.comment_text}>
        <div className={style.nickname}>HAMSTER</div>
        <div className={style.content}>너무 즐거워 보여 하하하하하하하</div>
      </div>
    </div>
  );
};

export default CommentItem;
