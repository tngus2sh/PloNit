import React from "react";
import style from "styles/css/App.module.css";
import NavBar from "components/common/NavBar";
import RouteComponent from "pages/lib/index";

import useEffectApp from "useEffects/useEffectApp";
import useEffectApp_Crewping from "useEffects/useEffectApp_crewping";

import { useSelector } from "react-redux";
import { rootState } from "store/store";
// import "./firebase-messaging-sw.js";

// 부드러운 애니메이션 (https://animate.style/)
import "animate.css";

function App() {
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const windowWidth = useSelector<rootState, number>((state) => {
    return state.window.width;
  });

  useEffectApp();
  // useEffectApp_Crewping();

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
