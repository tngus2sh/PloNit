import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import style from "styles/css/MyRankPage/MyRankMain.module.css";
import { MyRankInterface } from "interface/rankInterface";
import { getMyRanking } from "api/lib/members";

const MyRankMain = () => {
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isMyRanking, setMyRanking] = useState<MyRankInterface[]>([]);

  useEffect(() => {
    getMyRanking(
      accessToken,
      (res) => {
        console.log("나의 랭킹 조회 성공");
        console.log(res.data);
        setMyRanking(res.data.resultBody);
      },
      (err) => {
        console.log("나의 랭킹 조회 실패", err);
      },
    );
  }, []);
  console.log(isMyRanking);

  return (
    <div className={style.mymain}>
      <div className={style.left}>
        <div className={style.title}>순위</div>
        <div>
          <span className={style.large}>27</span>위
        </div>
      </div>
      <div className={style.middle}>
        <img src="/metamong.png" alt="몽" />
        <div>메타몽</div>
      </div>
      <div className={style.right}>
        <div className={style.title}>누적 거리</div>
        <div>
          <span className={style.large}>10.27</span>km
        </div>
      </div>
    </div>
  );
};

export default MyRankMain;
