import React, { useState, useEffect, useRef } from "react";
import getGPS from "components/plogging/functions/getGPS";
import { ploggingType } from "types/ploggingTypes";
import Swal from "sweetalert2";

import { useNavigate, useLocation } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import { setWindowHeight, setWindowWidth } from "store/window-slice";
import * as P from "store/plogging-slice";

const intervalTime = 2;

function useEffectApp() {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const interval = useRef<NodeJS.Timeout | null>(null);
  const useTimer = useSelector<rootState, boolean>((state) => {
    return (
      state.plogging.ploggingType != "none" &&
      !state.plogging.isEnd &&
      !state.plogging.beforeEnd
    );
  });
  const second = useSelector<rootState, number>((state) => {
    return state.plogging.second;
  });
  const minute = useSelector<rootState, number>((state) => {
    return state.plogging.minute;
  });
  const nowType = useSelector<rootState, ploggingType>((state) => {
    return state.plogging.ploggingType;
  });
  const volTakePicture = useSelector<rootState, boolean>((state) => {
    return state.plogging.volTakePicture;
  });

  const workerRef = useRef<Worker | null>(null);
  const [workerNA, setWorkerNA] = useState<boolean>(true);

  useEffect(() => {
    function handleResize() {
      dispatch(setWindowHeight(window.innerHeight));
      dispatch(setWindowWidth(window.innerWidth));
    }
    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  useEffect(() => {
    if (useTimer) {
      getGPS()
        .then((response) => {
          const { latitude, longitude } = response.coords;
          dispatch(P.addPath({ latitude: latitude, longitude: longitude }));

          function addTime() {
            interval.current = setInterval(() => {
              dispatch(P.addTime());
            }, 1000);
          }
          async function sendGPS() {
            getGPS()
              .then((response) => {
                const { latitude, longitude } = response.coords;
                dispatch(
                  P.addPath({ latitude: latitude, longitude: longitude }),
                );
              })
              .catch((error) => {
                console.error(error);
              });
          }

          if (window.Worker) {
            const broadcast1 = new BroadcastChannel("timer1");
            broadcast1.postMessage("test");

            workerRef.current = new Worker(
              new URL(`workers/worker.js`, import.meta.url),
            );
            setWorkerNA(false);
            workerRef.current.postMessage("start");
            workerRef.current.postMessage("start2");
            workerRef.current.onmessage = (event) => {
              if (event.data === "tick") {
                dispatch(P.addTime());
              }
              if (event.data === "tick2") {
                sendGPS();
              }
            };
          } else {
            addTime();
          }
        })
        .catch((error) => {
          console.error(error);
        });
    } else {
      if (interval.current) {
        clearInterval(interval.current);
      }
      workerRef.current?.terminate();
    }

    return () => {
      workerRef.current?.terminate();
    };
  }, [useTimer]);

  useEffect(() => {
    if (workerNA && useTimer && second % intervalTime === 0) {
      async function sendGPS() {
        getGPS()
          .then((response) => {
            const { latitude, longitude } = response.coords;
            dispatch(P.addPath({ latitude: latitude, longitude: longitude }));
          })
          .catch((error) => {
            console.error(error);
          });
      }

      sendGPS();
    }
  }, [second]);

  useEffect(() => {
    if (nowType === "VOL" && minute >= 1 && !volTakePicture) {
      Swal.fire({
        icon: "info",
        title: "중간 사진 촬영",
        html: "<div>지금까지 플로깅한 봉투<br/>사진을 찍어주세요.</div>",
        confirmButtonText: "확인",
        confirmButtonColor: "#2CD261",
        didClose: () => {
          dispatch(P.setVolTakePicture(true));
          if (location.pathname !== "/plogging") {
            navigate("/plogging");
          }
        },
      });
    }
  }, [minute]);
}

export default useEffectApp;
