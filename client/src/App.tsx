import React from "react";
import style from "styles/css/App.module.css";
import NavBar from "components/common/NavBar";
import RouteComponent from "pages/lib/index";
import useEffectApp from "useEffects/useEffectApp";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

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
