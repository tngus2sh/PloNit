import React from "react";
import DefaultMap from "components/plogging/DefaultMap";
import DefaultPathMap from "components/plogging/DefaultPathMap";

import { ploggingType } from "types/ploggingTypes";
import BeforeStart from "components/plogging/PloggingPage/BeforeStart";
import SoloJog from "components/plogging/PloggingPage/SoloJog";
import SoloPlocka from "components/plogging/PloggingPage/SoloPlocka";
import Crewping from "components/plogging/PloggingPage/Crewping";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

function getComponent(ploggingType: ploggingType): JSX.Element {
  switch (ploggingType) {
    case "solo-jog":
      return <SoloJog />;
    case "solo-plocka":
      return <SoloPlocka />;
    case "crewping":
      return <Crewping />;
    default:
      return <BeforeStart />;
  }
}

const PloggingPage = () => {
  const componentType: ploggingType = useSelector<rootState, ploggingType>(
    (state) => {
      return state.plogging.ploggingType;
    },
  );
  const Component = getComponent(componentType);

  return (
    <div>
      {/* <DefaultMap subHeight={200} isBefore={false} /> */}
      {/* <DefaultPathMap subHeight={0}></DefaultPathMap> */}
      {Component}
    </div>
  );
};

export default PloggingPage;
