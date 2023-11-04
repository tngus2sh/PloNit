import React, { useEffect } from "react";

import { ploggingType } from "types/ploggingTypes";
import BeforeStart from "components/plogging/PloggingPage/BeforeStart";
import SoloJog from "components/plogging/PloggingPage/SoloJog";
import SoloPlocka from "components/plogging/PloggingPage/SoloPlocka";
import Crewping from "components/plogging/PloggingPage/Crewping";

import { useNavigate } from "react-router-dom";
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
  const navigate = useNavigate();
  const isEnd = useSelector<rootState, boolean>((state) => {
    return state.plogging.isEnd;
  });
  const componentType: ploggingType = useSelector<rootState, ploggingType>(
    (state) => {
      return state.plogging.ploggingType;
    },
  );
  const Component = getComponent(componentType);

  useEffect(() => {
    if (isEnd) {
      navigate("/plogging/complete");
    }
  }, [isEnd]);

  return <div>{!isEnd && Component}</div>;
};

export default PloggingPage;
