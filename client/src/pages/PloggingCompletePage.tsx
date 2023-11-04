import React, { useState, useEffect } from "react";
import PloggingComplete from "components/plogging/PloggingCompletePage/PloggingComplete";
import PloggingVolunteerInput from "components/plogging/PloggingCompletePage/PloggingVolunteerInput";

import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { rootState } from "store/store";

const PloggingCompletePage = () => {
  const navigate = useNavigate();
  const isEnd = useSelector<rootState, boolean>((state) => {
    return state.plogging.isEnd;
  });
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });

  function getComponent() {
    return <></>;
  }

  const [infoUploaded, setInfoUploaded] = useState<boolean>(false);

  // 플러깅이 아닐 시 접근하면 돌려보내기
  useEffect(() => {
    if (!isEnd) {
      navigate("/plogging");
    }
  }, []);

  return (
    <div style={{ height: windowHeight }}>{isEnd && <PloggingComplete />}</div>
  );
};

export default PloggingCompletePage;
