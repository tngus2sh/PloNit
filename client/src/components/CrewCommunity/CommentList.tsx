import React from "react";
import CommentItem from "./CommentItem";
import { FeedInterface, CommentInterface } from "interface/crewInterface";

const CommentList = ({ feed }: { feed: FeedInterface }) => {
  const isCommentList: CommentInterface[] = feed.comments;
  return (
    <div>
      {isCommentList.map((comment, index) => (
        <CommentItem key={index} comment={comment} />
      ))}
    </div>
  );
};

export default CommentList;
