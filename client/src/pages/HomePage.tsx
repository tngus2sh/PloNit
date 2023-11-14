import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { LogoTopBar } from "components/common/TopBar";
import HomeBanner from "components/Home/HomeBanner";
import Carousel from "components/Home/Carousel";

const HomePage = () => {
  const navigate = useNavigate();
  const isLogined = useSelector((state: any) => state.user.auth.isLogin);
  const User = useSelector((state: any) => state.user.info);
  // useEffect(() => {
  //   if (!isLogined) {
  //     navigate("/login");
  //   }
  // }, []);
  return (
    <div>
      <LogoTopBar />
      <HomeBanner />
      <div
        style={{
          textAlign: "left",
          marginLeft: "1.5rem",
          marginTop: "2rem",
          marginBottom: "2rem",
        }}
      >
        <div style={{ fontSize: "1.1rem" }}>
          <span style={{ fontSize: "1.6rem", fontWeight: "600" }}>
            {User.nickname}
          </span>
          님의
        </div>
        <div style={{ fontSize: "1.8rem", fontWeight: "600" }}>크루핑</div>
      </div>
      <div>
        <Carousel />
      </div>
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default HomePage;
