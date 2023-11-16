import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";

import style from "styles/css/CrewCommunityPage/CrewpingList.module.css";

import CrewpingItem from "./CrewpingItem";
import { CrewpingInterface } from "interface/crewInterface";
import { getCrewpingList } from "api/lib/crewping";

const CrewpingList = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const { crewId } = useParams();
  const [isCrewpingList, setCrewpingList] = useState<CrewpingInterface[]>([]);

  useEffect(() => {
    getCrewpingList(
      accessToken,
      Number(crewId),
      (res) => {
        console.log("크루핑 조회 성공");
        console.log(res.data);
        setCrewpingList(res.data.resultBody);
      },
      (err) => {
        console.log("크루핑 조회 실패", err);
        const response = [
          {
            crewpingId: 14,
            name: "키키키키키",
            crewpingImage:
              "https://plonitbucket.s3.ap-northeast-2.amazonaws.com/crewping/crewpingImage/b3045ae8-49f7-4782-8171-022f70dd77e5kakao_login2.png",
            place: "서울 성북구 지봉로 171 ㄴ",
            startDate: "2023-11-14 17:00",
            endDate: "2023-11-14 21:30",
            cntPeople: 2,
            maxPeople: 2,
          },
          {
            crewpingId: 35,
            name: "수완동 크루핑",
            crewpingImage:
              "https://plonitbucket.s3.ap-northeast-2.amazonaws.com/crewping/crewpingImage/1c77da31-04b4-4b1e-a31f-bf32c97e49a8%ED%9A%9F%EC%88%98_100.png",
            place: "광주 광산구 도천남길 41-60 ㅇ",
            startDate: "2023-11-17 10:30",
            endDate: "2023-11-17 12:00",
            cntPeople: 1,
            maxPeople: 5,
          },
          {
            crewpingId: 19,
            name: "수완동 같이 달려요!",
            crewpingImage:
              "https://plonitbucket.s3.ap-northeast-2.amazonaws.com/crewping/crewpingImage/30c1fd7d-4b44-48d8-bf3d-18b41e8b4d9bdelete.png",
            place: "광주 광산구 장신로 98",
            startDate: "2023-11-17 14:00",
            endDate: "2023-11-17 15:00",
            cntPeople: 2,
            maxPeople: 4,
          },
          {
            crewpingId: 34,
            name: "오늘도 달리기",
            crewpingImage:
              "https://plonitbucket.s3.ap-northeast-2.amazonaws.com/crewping/crewpingImage/76639c43-a604-4a4f-8457-64ac2703fac3image.png",
            place: "부산 해운대구 APEC로 55 우리집",
            startDate: "2023-11-20 10:00",
            endDate: "2023-11-30 11:30",
            cntPeople: 1,
            maxPeople: 3,
          },
          {
            crewpingId: 15,
            name: "오늘도 달리기",
            crewpingImage:
              "https://plonitbucket.s3.ap-northeast-2.amazonaws.com/crewping/crewpingImage/81ee2bb6-a6c8-4463-ad4e-ebdbd8db7567Image%20%2820%29.png",
            place: "광주 광산구 평동3차진입로 123 ㅎㅎ",
            startDate: "2023-11-20 14:00",
            endDate: "2023-11-21 14:00",
            cntPeople: 2,
            maxPeople: 2,
          },
        ];
        setCrewpingList(response);
      },
    );
  }, []);

  return (
    <div>
      <div className={style.crewping_list_container}>
        {isCrewpingList ? (
          <>
            {isCrewpingList.map((crewping, index) => (
              <CrewpingItem key={index} crewping={crewping} />
            ))}
          </>
        ) : (
          <div style={{ marginTop: "2rem" }}>크루핑이 존재하지 않습니다.</div>
        )}
      </div>

      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewpingList;
