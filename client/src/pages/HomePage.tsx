import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { LogoTopBar } from "components/common/TopBar";
import HomeBanner from "components/Home/HomeBanner";
import Carousel from "components/Home/Carousel";

interface Card {
  id: number;
  content: string;
}

const cardList: Card[] = [
  { id: 1, content: "Card 1" },
  { id: 2, content: "Card 2" },
  { id: 3, content: "Card 3" },
  { id: 4, content: "Card 4" },
  { id: 5, content: "Card 5" },
];

const HomePage = () => {
  const navigate = useNavigate();
  const isLogined = useSelector((state: any) => state.user.isLogin);

  useEffect(() => {
    if (!isLogined) {
      navigate("/login");
    }
  }, []);
  return (
    <div>
      <LogoTopBar />
      <HomeBanner />
      <div>나의 크루 플로깅</div>
      <div>
        <Carousel card_list={cardList} />
      </div>
    </div>
  );
};

export default HomePage;
