import React from "react";

import { ploggingType } from "types/ploggingTypes";
import BeforeStart from "components/plogging/PloggingPage/BeforeStart";
import SoloJog from "components/plogging/PloggingPage/SoloJog";
import SoloPlocka from "components/plogging/PloggingPage/SoloPlocka";
import Crewping from "components/plogging/PloggingPage/Crewping";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

function getComponent(ploggingType: ploggingType): JSX.Element {
  switch (ploggingType) {
    case "IND":
      return <SoloJog />;
    case "VOL":
      return <SoloPlocka />;
    case "CREWPING":
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

  return <div>{Component}</div>;
};

export default PloggingPage;
