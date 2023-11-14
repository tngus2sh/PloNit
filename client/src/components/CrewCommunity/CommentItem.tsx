import React from "react";
import { useSelector, useDispatch } from "react-redux";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCommunityPage/CommentModal.module.css";
import { getCommentDelete } from "api/lib/feed";
import { CommentInterface } from "interface/crewInterface";
import {
  NotOkModal,
  OkModal,
  QuestionModal,
} from "components/common/AlertModals";

const CommentItem = ({
  comment,
  fetchFeedList,
}: {
  comment: CommentInterface;
  fetchFeedList: () => void;
}) => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const handleDeleteComment = () => {
    QuestionModal({ text: "댓글을 삭제하시겠습니까" }).then((res) => {
      if (res.isConfirmed) {
        if (comment.commentId) {
          getCommentDelete(
            accessToken,
            comment.commentId,
            (res) => {
              console.log(res.data);
              console.log("댓글 삭제 성공");
              OkModal({ text: "댓글이 삭제되었습니다." });
              fetchFeedList();
            },
            (err) => {
              console.log("댓글 삭제 에러", err);
            },
          );
        }
      }
    });
  };
  return (
    <div className={style.comment_item}>
      <div className={style.right}>
        <div className={style.comment_img}>
          <img src={comment.profileImage} alt="프로필" />
        </div>
        <div className={style.comment_text}>
          <div className={style.nickname}>{comment.nickname}</div>
          <div className={style.content}>{comment.content}</div>
        </div>
      </div>
      {comment.isMine ? (
        <Icon
          icon="bi:x"
          style={{ width: "1.8rem", height: "1.8rem" }}
          className={style.x_icon}
          onClick={handleDeleteComment}
        />
      ) : null}
    </div>
  );
};

export default CommentItem;
