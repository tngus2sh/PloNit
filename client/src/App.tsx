import React, { useState, useEffect } from "react";
import style from "styles/css/App.module.css";
import NavBar from "components/common/NavBar";
import RouteComponent from "pages/lib/index";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import { setWindowHeight } from "store/windowHeight-slice";

function App() {
  const dispatch = useDispatch();
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.windowHeight.value;
  });

  useEffect(() => {
    function handleResize() {
      dispatch(setWindowHeight(window.innerHeight));
    }
    window.addEventListener("resize", handleResize);

    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  return (
    <div className={style.App} style={{ height: `${windowHeight}px` }}>
      <RouteComponent />
      <div className={style.navBar}>
        <NavBar />
      </div>
    </div>
  );
}

export default App;
