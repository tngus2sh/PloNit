import React from "react";
import style from "styles/css/MyPloggingDetailPage.module.css";
import { BackTopBar } from "components/common/TopBar";

const MyPloggingDetailPage = () => {
  return (
    <div>
      <BackTopBar text="나의 플로깅" />
      <div className={style.myplogging_detail}>
        <div className={style.date_area}>
          <div>2023년</div>
          <div>10월 15일의 기록</div>
        </div>
        <div className={style.profile}>
          <img src="/metamong.png" alt="몽" />
          <div className={style.text}>
            <div className={style.nickname}>
              <span className={style.large}>빵빵덕</span> 님
            </div>
            <div>2023.10.15 19:27 수완동, 광주</div>
          </div>
        </div>
        <div className={style.myrecord}>
          <div className={style.dist}>
            <div>총 거리</div>
            <div className={style.record_large}>2.47</div>
          </div>
          <div className={style.time}>
            <div>총 시간</div>
            <div className={style.record_large}>42:37</div>
          </div>
          <div className={style.calorie}>
            <div>칼로리</div>
            <div className={style.record_large}>354</div>
          </div>
        </div>
        <div className={style.content}>
          날이 좋아서 플로깅을 다녀왔다! 상쾌한 하루였다!! ㅏ하핳하핳하ㅏㅎ
        </div>
        <div className={style.img_area}>
          <img src="/feed_img.png" alt="이미지" />
          <img src="/crewbg.png" alt="이미지" />
        </div>
      </div>
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default MyPloggingDetailPage;
