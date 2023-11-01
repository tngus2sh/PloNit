import React from "react";
import DefaultMap from "../DefaultMap";
import InfoDiv from "../ploggingComps/InfoDiv";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

const SoloJog = () => {
  const infoDivHeight = useSelector<rootState, number>((state) => {
    const windowHeight = state.window.height;
    return windowHeight * 0.25;
  });
  return (
    <DefaultMap subHeight={infoDivHeight} isBefore={false}>
      <InfoDiv infoDivHeight={infoDivHeight} />
    </DefaultMap>
  );
};

export default SoloJog;
