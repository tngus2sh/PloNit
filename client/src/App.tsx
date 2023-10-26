import React from "react";
import style from "styles/css/App.module.css";
import NavBar from "components/common/NavBar";
import RouteComponent from "pages/lib/index";

const innerHeight = window.innerHeight;

function App() {
  return (
    <div className={style.App} style={{ height: `${innerHeight}px` }}>
      <RouteComponent />
      <div className={style.navBar}>
        <NavBar />
      </div>
    </div>
  );
}

export default App;
