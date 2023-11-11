import React, { useEffect } from "react";
import useSocket from "components/plogging/functions/useSocket";
import CommonButton from "components/common/CommonButton";

import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import * as P from "store/plogging-slice";
import * as Crewping from "store/crewping-slice";

const Test = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(Crewping.clear());
    dispatch(P.clear());
    dispatch(Crewping.setRoomId("박주성"));
    dispatch(Crewping.setCharge(true));
    dispatch(
      Crewping.setUserImages({
        박주성1:
          "https://post-phinf.pstatic.net/MjAyMDExMDRfODgg/MDAxNjA0NDUyNDkwNTk5.3qW-hU8R0DvdQW8bgDuldGPN27uFdDvTh7haVQTpDqgg.2Tj5OcLz-xJx4rAFgkN48q8w5hrN5QahTK_DDIsNo2Ig.PNG/VneKfm5.png?type=w800_q75",
        박주성2:
          "https://post-phinf.pstatic.net/MjAyMDExMDRfODgg/MDAxNjA0NDUyNDkwNTk5.3qW-hU8R0DvdQW8bgDuldGPN27uFdDvTh7haVQTpDqgg.2Tj5OcLz-xJx4rAFgkN48q8w5hrN5QahTK_DDIsNo2Ig.PNG/VneKfm5.png?type=w800_q75",
        박주성3:
          "https://post-phinf.pstatic.net/MjAyMDExMDRfODgg/MDAxNjA0NDUyNDkwNTk5.3qW-hU8R0DvdQW8bgDuldGPN27uFdDvTh7haVQTpDqgg.2Tj5OcLz-xJx4rAFgkN48q8w5hrN5QahTK_DDIsNo2Ig.PNG/VneKfm5.png?type=w800_q75",
        박주성4:
          "https://post-phinf.pstatic.net/MjAyMDExMDRfODgg/MDAxNjA0NDUyNDkwNTk5.3qW-hU8R0DvdQW8bgDuldGPN27uFdDvTh7haVQTpDqgg.2Tj5OcLz-xJx4rAFgkN48q8w5hrN5QahTK_DDIsNo2Ig.PNG/VneKfm5.png?type=w800_q75",
        박주성5:
          "https://post-phinf.pstatic.net/MjAyMDExMDRfODgg/MDAxNjA0NDUyNDkwNTk5.3qW-hU8R0DvdQW8bgDuldGPN27uFdDvTh7haVQTpDqgg.2Tj5OcLz-xJx4rAFgkN48q8w5hrN5QahTK_DDIsNo2Ig.PNG/VneKfm5.png?type=w800_q75",
        박주성6:
          "https://post-phinf.pstatic.net/MjAyMDExMDRfODgg/MDAxNjA0NDUyNDkwNTk5.3qW-hU8R0DvdQW8bgDuldGPN27uFdDvTh7haVQTpDqgg.2Tj5OcLz-xJx4rAFgkN48q8w5hrN5QahTK_DDIsNo2Ig.PNG/VneKfm5.png?type=w800_q75",
      }),
    );
    dispatch(P.setBeforeCrewping(true));
    navigate("/plogging");
  }, []);

  return <></>;
};

export default Test;
