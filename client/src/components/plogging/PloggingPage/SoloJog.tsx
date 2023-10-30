import React from "react";
import Swal from "sweetalert2";
import DefaultMap from "../DefaultMap";
import InfoDiv from "./InfoDiv";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

const SoloJog = () => {
  const infoDivHeight = useSelector<rootState, number>((state) => {
    const windowHeight = state.windowHeight.value;
    return windowHeight * 0.25;
  });
  return (
    <DefaultMap subHeight={infoDivHeight} isBefore={false}>
      <InfoDiv infoDivHeight={infoDivHeight} />
    </DefaultMap>
  );
};

export default SoloJog;
