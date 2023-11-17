import React from "react";
import CommentItem from "./CommentItem";
import { FeedInterface, CommentInterface } from "interface/crewInterface";

const CommentList = ({
  feed,
  fetchFeedList,
}: {
  feed: FeedInterface;
  fetchFeedList: () => void;
}) => {
  const isCommentList: CommentInterface[] = feed.comments;
  return (
    <div>
      {isCommentList.map((comment, index) => (
        <CommentItem
          key={index}
          comment={comment}
          fetchFeedList={fetchFeedList}
        />
      ))}
    </div>
  );
};

export default CommentList;
