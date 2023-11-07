import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import FeedItem from "./FeedItem";
import { CrewInterface } from "interface/crewInterface";
import { getFeedList } from "api/lib/feed";

const CrewFeed = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const [isFeedList, setFeedList] = useState<CrewInterface[]>([]);

  useEffect(() => {
    getFeedList(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("피드 조회 성공");
        console.log(res);
        console.log(res.data);
        console.log(res.data.resultBody);
        setFeedList(res.data.resultBody);
      },
      (err) => {
        console.log("피드 조회 실패", err);
      },
    );
  }, []);

  return (
    <div>
      {isFeedList.map((feed, index) => (
        <FeedItem key={index} feed={feed} />
      ))}
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewFeed;
