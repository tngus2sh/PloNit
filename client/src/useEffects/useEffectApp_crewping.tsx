import React, { useEffect, useRef } from "react";
import getGPS from "components/plogging/functions/getGPS";
import { ploggingType } from "types/ploggingTypes";
import useSocket from "components/plogging/functions/useSocket";
import { Client } from "@stomp/stompjs";
import Swal from "sweetalert2";

import { useNavigate, useLocation } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";
import * as Crewping from "store/crewping-slice";

import { startPlogging } from "api/lib/plogging";

function useEffectApp_Crewping() {
  const navigate = useNavigate();
  const location = useLocation();
  const dispatch = useDispatch();
  const interval = useRef<NodeJS.Timeout | null>(null);
  const stompClient = useRef<Client | null>(null);
  const nowType = useSelector<rootState, ploggingType>((state) => {
    return state.plogging.ploggingType;
  });
  const roomId = useSelector<rootState, string>((state) => {
    return state.crewping.roomId;
  });
  const senderId = useSelector<rootState, string>((state) => {
    return state.crewping.senderId;
  });
  const isLoading = useSelector<rootState, boolean>((state) => {
    return state.crewping.isLoading;
  });
  const crewpingStart = useSelector<rootState, boolean>((state) => {
    return state.crewping.crewpingStart;
  });
  const crewpingEnd = useSelector<rootState, boolean>((state) => {
    return state.crewping.crewpingEnd;
  });
  const accessToken = useSelector<rootState, string>((state) => {
    return state.user.auth.accessToken;
  });
  const weight = useSelector<rootState, number>((state) => {
    return state.user.info.weight;
  });
  const membersLen = useSelector<rootState, number>((state) => {
    return Object.keys(state.crewping.members).length;
  });
  const workerRef = useRef<Worker | null>(null);

  const { setToggleSocket } = useSocket({ stompClient, roomId, senderId });

  useEffect(() => {
    if (isLoading) {
      setToggleSocket(true);
      function getLocation() {
        interval.current = setInterval(() => {
          dispatch(Crewping.setGetLocation(true));
        }, 30000);
      }

      if (window.Worker) {
        workerRef.current = new Worker(
          new URL(`workers/worker.js`, import.meta.url),
        );
        workerRef.current.postMessage("start30");
        workerRef.current.onmessage = (event) => {
          if (event.data === "tick30") {
            dispatch(Crewping.setGetLocation(true));
          }
        };
      } else {
        getLocation();
      }
    }

    return () => {
      workerRef.current?.terminate();
    };
  }, [isLoading]);

  useEffect(() => {
    if (crewpingStart) {
      if (weight > 0) {
        dispatch(P.setKg(weight));
      }
      getGPS().then((response) => {
        const { latitude, longitude } = response.coords;
        startPlogging({
          accessToken: accessToken,
          type: "CREWPING",
          latitude: latitude,
          longitude: longitude,
          success: (response) => {
            console.log("크루핑 요청!");
            console.log(response);
            dispatch(P.setPloggingId(response.data.resultBody));
            dispatch(P.setPloggingType("CREWPING"));
            dispatch(P.setBeforeCrewping(false));
            dispatch(P.setPeople(membersLen));
            if (location.pathname !== "/plogging") {
              navigate("/plogging");
            }
          },
          fail: (error) => {
            console.error(error);
          },
        });
      });
    }
  }, [crewpingStart]);

  useEffect(() => {
    if (crewpingEnd) {
      dispatch(P.setIsEnd(true));
      navigate("/plogging/complete");
      stompClient.current?.deactivate();
      if (interval.current) {
        clearInterval(interval.current);
      }

      workerRef.current?.terminate();
    }
  }, [crewpingEnd]);
}

export default useEffectApp_Crewping;
