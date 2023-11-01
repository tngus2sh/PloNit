import React, { useState, useEffect, useRef } from "react";
import style from "styles/css/App.module.css";
import NavBar from "components/common/NavBar";
import RouteComponent from "pages/lib/index";
import useGPS from "components/plogging/functions/useGPS";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import { setWindowHeight, setWindowWidth } from "store/window-slice";
import * as P from "store/plogging-slice";

// 부드러운 애니메이션 (https://animate.style/)
import "animate.css";

const intervalTime = 10;

function App() {
  const dispatch = useDispatch();
  const interval = useRef<NodeJS.Timeout | null>(null);
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const windowWidth = useSelector<rootState, number>((state) => {
    return state.window.width;
  });
  const isBefore = useSelector<rootState, boolean>((state) => {
    return state.plogging.ploggingType === "none";
  });
  const second = useSelector<rootState, number>((state) => {
    return state.plogging.second;
  });
  const { latitude, longitude, onSearch, setOnSearch } = useGPS();

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
    if (!isBefore) {
      interval.current = setInterval(() => {
        dispatch(P.addTime());
      }, 1000);
    } else {
      if (interval.current) {
        clearInterval(interval.current);
      }
    }
  }, [isBefore]);

  useEffect(() => {
    if (!isBefore && second % intervalTime === 0) {
      setOnSearch(true);
    }
  }, [second]);

  useEffect(() => {
    if (!isBefore && !onSearch) {
      dispatch(P.addPath({ latitude: latitude, longitude: longitude }));
    }
  }, [onSearch]);

  return (
    <div
      className={style.App}
      style={{ height: `${windowHeight}px`, width: `${windowWidth}px` }}
    >
      <RouteComponent />
      <div className={style.navBar}>
        <NavBar />
      </div>
    </div>
  );
}

export default App;
