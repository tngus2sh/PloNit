import React from "react";
import DefaultPathMap from "../DefaultPathMap";
import { BasicTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
import PloggingInfo from "../ploggingComps/PloggingInfo";
import { Coordinate } from "interface/ploggingInterface";

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
    <div style={{ width: "100%" }}>
      <BasicTopBar text="플로깅 완료" />
      <div
        style={{
          width: "100%",
          padding: "0 2.5%",
          boxSizing: "border-box",
        }}
      >
        <div>
          <div>오늘도</div>
          <div>플로깅 완료!!</div>
        </div>
        <div></div>
      </div>
    </div>
  );
};

export default PloggingComplete;
