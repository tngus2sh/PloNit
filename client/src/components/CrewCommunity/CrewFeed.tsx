import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import FeedItem from "./FeedItem";
import { FeedInterface } from "interface/crewInterface";
import { getFeedList } from "api/lib/feed";

const CrewFeed = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const [isFeedList, setFeedList] = useState<FeedInterface[]>([]);

  const fetchFeedList = () => {
    getFeedList(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("피드 조회 성공");
        console.log(res.data);
        setFeedList(res.data.resultBody);
      },
      (err) => {
        console.log("피드 조회 실패", err);
      },
    );
  };

  useEffect(() => {
    fetchFeedList();
  }, []);

  return (
    <div>
      {isFeedList.map((feed, index) => (
        <FeedItem key={index} feed={feed} fetchFeedList={fetchFeedList} />
      ))}
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewFeed;
