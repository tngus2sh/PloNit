import React from "react";
import DefaultMap from "../DefaultMap";
import Swal from "sweetalert2";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

const InfoDiv = () => {
  return <div>ㅎㅇㅎㅇ</div>;
};

const SoloJog = () => {
  const infoDivHeight = useSelector<rootState, number>((state) => {
    const windowHeight = state.windowHeight.value;
    return windowHeight * 0.25;
  });
  return (
    <DefaultMap subHeight={infoDivHeight} isBefore={false}>
      <InfoDiv />
    </DefaultMap>
  );
};

export default SoloJog;
