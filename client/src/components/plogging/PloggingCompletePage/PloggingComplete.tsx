import React from "react";
import DefaultPathMap from "../DefaultPathMap";
import { BasicTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
import PloggingInfo from "../ploggingComps/PloggingInfo";
import PloggingSwiper from "../ploggingComps/PloggingSwiper";
import { Coordinate } from "interface/ploggingInterface";
import style from "styles/css/PloggingPage/PloggingComplete.module.css";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

function formatNumber(n: number): string {
  if (n < 10) {
    return "0" + n;
  }

  return n.toString();
}

const PloggingComplete = () => {
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const paths = useSelector<rootState, Coordinate[]>((state) => {
    return state.plogging.paths;
  });
  const distance = useSelector<rootState, number>((state) => {
    return state.plogging.distance;
  });
  const minute = useSelector<rootState, number>((state) => {
    return state.plogging.minute;
  });
  const second = useSelector<rootState, number>((state) => {
    return state.plogging.second;
  });
  const calorie = useSelector<rootState, number>((state) => {
    return state.plogging.calorie;
  });
  const images = useSelector<rootState, string[]>((state) => {
    return state.plogging.images;
  });
  const time = new Date();
  const year = time.getFullYear();
  const month = time.getMonth() + 1;
  const date = time.getDate();
  const day = ["일", "월", "화", "수", "목", "금", "토"][time.getDay()];

  return (
    <div
      style={{
        width: "100%",
        height: `${windowHeight - 56}px`,
        overflowY: "scroll",
      }}
    >
      <BasicTopBar text="플로깅 완료" />
      <div
        style={{
          padding: "0 5%",
          boxSizing: "border-box",
        }}
      >
        <div className={style.text}>
          <div>오늘도</div>
          <div>플로깅 완료!!</div>
        </div>
        <div
          style={{
            height: "auto",
            width: "100%",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <DefaultPathMap paths={paths} subHeight={0} />
        </div>
        <div className={style.text}>
          <div>{`${year}년`}</div>
          <div>{`${month}월 ${formatNumber(date)}일 ${day}요일`}</div>
        </div>
        <div
          style={{
            width: "100%",
            height: windowHeight * 0.0625,
            display: "flex",
          }}
        >
          <PloggingInfo infoLabel="km" infoValue={distance} />
          <PloggingInfo
            infoLabel="시간"
            infoValue={`${minute}:${formatNumber(second)}`}
          />
          <PloggingInfo infoLabel="칼로리" infoValue={calorie} />
        </div>
        <br />
        <div className={style.text_sm}>{`플로깅 사진`}</div>
        <PloggingSwiper
          images={images}
          onClick={() => {
            console.log(123);
          }}
        />
      </div>
    </div>
  );
};

export default PloggingComplete;
