import React from "react";
import DefaultPathMap from "../DefaultPathMap";
import { BasicTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
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

  return (
    <div style={{ width: "100%" }}>
      <BasicTopBar text="플로깅 완료" />
    </div>
  );
};

export default PloggingComplete;
