import React from "react";
import { LogoTopBar } from "components/common/TopBar";
import HomeBanner from "components/Home/HomeBanner";
import CrewPloggingCard from "components/Home/CrewPloggingCard";

const HomePage = () => {
  return (
    <div>
      <LogoTopBar />
      <HomeBanner />
      <div>나의 크루 플로깅</div>
      <div>
        <CrewPloggingCard />
      </div>
    </div>
  );
};

export default HomePage;
