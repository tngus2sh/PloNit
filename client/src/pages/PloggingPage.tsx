import React, { useEffect } from "react";

import { ploggingType } from "types/ploggingTypes";
import BeforeStart from "components/plogging/PloggingPage/BeforeStart";
import SoloJog from "components/plogging/PloggingPage/SoloJog";
import SoloPlocka from "components/plogging/PloggingPage/SoloPlocka";
import BeforeCrewping from "components/plogging/PloggingPage/BeforeCrewping";
import Crewping from "components/plogging/PloggingPage/Crewping";

import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { rootState } from "store/store";

function getComponent(
  ploggingType: ploggingType,
  beforeCrewping: boolean,
  isLoading: boolean,
): JSX.Element {
  if (beforeCrewping) {
    if (isLoading) return <BeforeCrewping />;
    return <BeforeStart />;
  }

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
  const showComponent = useSelector<rootState, boolean>((state) => {
    return !state.plogging.isEnd && !state.plogging.beforeEnd;
  });
  const beforeCrewping = useSelector<rootState, boolean>((state) => {
    return state.plogging.beforeCrewping;
  });
  const isLoading = useSelector<rootState, boolean>((state) => {
    return state.crewping.isLoading;
  });
  const componentType: ploggingType = useSelector<rootState, ploggingType>(
    (state) => {
      return state.plogging.ploggingType;
    },
  );

  const Component = getComponent(componentType, beforeCrewping, isLoading);

  useEffect(() => {
    if (!showComponent) {
      navigate("/plogging/complete");
    }
  }, [showComponent]);

  return <div>{showComponent && Component}</div>;
};

export default PloggingPage;
