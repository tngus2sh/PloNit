import React, { useState, useEffect } from "react";
import PloggingComplete from "components/plogging/PloggingCompletePage/PloggingComplete";
import PloggingVolunteerInput from "components/plogging/PloggingCompletePage/PloggingVolunteerInput";

import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { rootState } from "store/store";

function getComponent(isEnd: boolean) {
  if (isEnd) {
    return <PloggingComplete />;
  }
  return <PloggingVolunteerInput />;
}

const PloggingCompletePage = () => {
  const navigate = useNavigate();
  const isEnd = useSelector<rootState, boolean>((state) => {
    return state.plogging.isEnd;
  });
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const showCompenent = useSelector<rootState, boolean>((state) => {
    return state.plogging.isEnd || state.plogging.beforeEnd;
  });
  const Component = getComponent(isEnd);

  // 플러깅이 아닐 시 접근하면 돌려보내기
  useEffect(() => {
    if (!showCompenent) {
      navigate("/plogging");
    }
  }, []);

  return (
    <div style={{ height: windowHeight }}>{showCompenent && Component}</div>
  );
};

export default PloggingCompletePage;
