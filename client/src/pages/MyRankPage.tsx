import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { BackTopBar } from "components/common/TopBar";
import MyRankMain from "components/MyRank/MyRankMain";
import MyRankItem from "components/MyRank/MyRankItem";
import style from "styles/css/MyRankPage.module.css";
import { MyRankInterface } from "interface/rankInterface";
import { getMyRanking } from "api/lib/members";

const formattedSeason = (datestr: any) => {
  const date = new Date(datestr);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const season = day === 1 ? 1 : 2;

  return `${year}년 ${month}월 ${season}시즌`;
};

const formattedDate = (datestr: any) => {
  const date = new Date(datestr);
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return `${month}월 ${day}일`;
};
const formattedDatePlus = (datestr: any) => {
  const date = new Date(datestr);
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return `${month}월 ${day}일`;
};
const endformattedDate = (datestr: any) => {
  const date = new Date(datestr);
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return `${month}월 ${day}일`;
};
const endformattedDatePlus = (datestr: any) => {
  const date = new Date(datestr);
  const month = date.getMonth() + 1;
  const day = date.getDate();

  if (day === 14) {
    const nextMonthFirstDay = new Date(date.getFullYear(), month, 1);
    const lastDay = new Date(nextMonthFirstDay.getTime() - 24 * 60 * 60 * 1000);
    return `${month}월 ${lastDay.getDate()}일`;
  } else {
    return `${month + 1}월 14일`;
  }
};

const MyRankPage = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isMyRanking, setMyRanking] = useState<MyRankInterface[]>([]);
  const [isNow, setNow] = useState<MyRankInterface>({} as MyRankInterface);
  console.log(isMyRanking[0]);
  useEffect(() => {
    getMyRanking(
      accessToken,
      (res) => {
        console.log("나의 랭킹 조회 성공");
        console.log(res.data);
        setMyRanking(res.data.resultBody);
        if (res.data.resultBody[0].isSeason) {
          setNow(res.data.resultBody[0]);
        } else {
          setNow({
            ranking: 0,
            distance: 0,
            startDate: res.data.resultBody[0].endDate,
            endDate: "",
            isSeason: true,
          });
        }
      },
      (err) => {
        console.log("나의 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isMyRanking);
  return (
    <div>
      {isNow.startDate ? (
        <>
          <BackTopBar text="나의 랭킹" />
          <div className={style.page_container}>
            <div className={style.myrank_container}>
              <div className={style.season_info_container}>
                {isNow.endDate === "" ? (
                  <>
                    <div className={style.season_title}>
                      {formattedSeason(isNow.startDate)}
                    </div>
                    <div className={style.season_date}>
                      ({formattedDatePlus(isNow.startDate)} ~
                      {endformattedDatePlus(isNow.startDate)})
                    </div>
                  </>
                ) : (
                  <>
                    <div className={style.season_title}>
                      {formattedSeason(isNow.startDate)}
                    </div>
                    <div className={style.season_date}>
                      ({formattedDate(isNow.startDate)} ~
                      {endformattedDate(isNow.endDate)})
                    </div>
                  </>
                )}
              </div>
              <div className={style.current_container}>
                <MyRankMain rank={isNow} />
              </div>
            </div>

            <div className={style.prev_container}>
              <div className={style.prev_info_container}>
                <div className={style.prev_title}>지난 랭킹</div>
              </div>

              <div className={style.prev_item_container}>
                {isMyRanking.map((data, index) => {
                  if (isMyRanking[0].isSeason) {
                    if (index >= 1) {
                      return <MyRankItem key={index} rank={data} />;
                    }
                    return null;
                  } else {
                    <MyRankItem key={index} rank={data} />;
                  }
                })}
              </div>
            </div>

            <div style={{ height: "4rem" }}></div>
          </div>
        </>
      ) : (
        <div>Loading...</div>
      )}
    </div>
  );
};

export default MyRankPage;
