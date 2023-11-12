import React from "react";
import { Routes, Route } from "react-router-dom";

import CrewCommunityPage from "pages/CrewCommunityPage";
import CrewCreatePage from "pages/CrewCreatePage";
import CrewDetailPage from "pages/CrewDetailPage";
import CrewListPage from "pages/CrewListPage";
import CrewMemberApprovalPage from "pages/CrewMemberApprovalPage";
import CrewMemberListPage from "pages/CrewMemberListPage";
import CrewpingCreatePage from "pages/CrewpingCreatePage";
import CrewpingDetailPage from "pages/CrewpingDetailPage";
import HomePage from "pages/HomePage";
import LoginPage from "pages/LoginPage";
import KakaoCallback from "pages/KakaoCallback";
import AddInfoPage from "pages/AddInfoPage";
import MyBadgePage from "pages/MyBadgePage";
import MyCrewPage from "pages/MyCrewPage";
import MyPloggingPage from "pages/MyPloggingPage";
import MyPloggingDetailPage from "pages/MyPloggingDetailPage";
import MyRankPage from "pages/MyRankPage";
import NotificationPage from "pages/NotificationPage";
import PageNotFound404 from "pages/PageNotFound404";
import PloggingCompletePage from "pages/PloggingCompletePage";
import PloggingPage from "pages/PloggingPage";
import ProfileEditPage from "pages/ProfileEditPage";
import ProfilePage from "pages/ProfilePage";
import RankingPage from "pages/RankingPage";
import VolunteerRegisterPage from "pages/VolunteerRegisterPage";
import FeedCreatePage from "pages/FeedCreatePage";
import CrewNoticePage from "pages/CrewNoticePage";

import PloggingImagePage from "pages/PloggingImagePage";
import Test from "./Test";

const RouteComponent = () => {
  return (
    <Routes>
      <Route path="/test" element={<Test />}></Route>
      <Route path="/" element={<HomePage />}></Route>
      <Route path="/login" element={<LoginPage />}></Route>
      <Route path="/plonit/auth/kakao" element={<KakaoCallback />}></Route>
      <Route path="/login/addinfo" element={<AddInfoPage />}></Route>
      <Route path="/profile" element={<ProfilePage />}></Route>
      <Route path="/profile/edit" element={<ProfileEditPage />}></Route>
      <Route path="/profile/crew" element={<MyCrewPage />}></Route>
      <Route path="/profile/plogging" element={<MyPloggingPage />}></Route>
      <Route path="/profile/rank" element={<MyRankPage />}></Route>
      <Route path="/profile/badge" element={<MyBadgePage />}></Route>
      <Route path="/plogging" element={<PloggingPage />}></Route>
      <Route path="/plogging/image" element={<PloggingImagePage />}></Route>
      <Route
        path="/plogging/complete"
        element={<PloggingCompletePage />}
      ></Route>
      <Route
        path="/plogging/volunteer"
        element={<VolunteerRegisterPage />}
      ></Route>
      <Route path="/ranking" element={<RankingPage />}></Route>
      <Route path="/notification" element={<NotificationPage />}></Route>
      <Route path="/crew/list" element={<CrewListPage />}></Route>
      <Route path="/crew/create" element={<CrewCreatePage />}></Route>
      {/* 아래 페이지는 수정할 수도 있음 */}
      <Route
        path="/profile/plogging/detail/:ploggingId"
        element={<MyPloggingDetailPage />}
      ></Route>
      <Route
        path="/crew/member/:crewId"
        element={<CrewMemberListPage />}
      ></Route>
      <Route
        path="/crew/community/:crewId"
        element={<CrewCommunityPage />}
      ></Route>
      <Route
        path="/crew/community/detail/:crewId"
        element={<CrewDetailPage />}
      ></Route>
      <Route
        path="/crew/community/approval/:crewId"
        element={<CrewMemberApprovalPage />}
      ></Route>
      <Route
        path="/crew/community/notice/:crewId"
        element={<CrewNoticePage />}
      ></Route>
      <Route
        path="/crew/crewping/create/:crewId"
        element={<CrewpingCreatePage />}
      ></Route>
      <Route
        path="/crew/crewping/detail/:crewpingId"
        element={<CrewpingDetailPage />}
      ></Route>
      <Route path="/feed/create/:crewId" element={<FeedCreatePage />}></Route>

      <Route path="/*" element={<PageNotFound404 />}></Route>
    </Routes>
  );
};

export default RouteComponent;
