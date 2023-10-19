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
import MyBadgePage from "pages/MyBadgePage";
import MyCrewPage from "pages/MyCrewPage";
import MyPloggingPage from "pages/MyPloggingPage";
import MyRankPage from "pages/MyRankPage";
import NotificationPage from "pages/NotificationPage";
import PageNotFound404 from "pages/PageNotFound404";
import PloggingCompletePage from "pages/PloggingCompletePage";
import PloggingPage from "pages/PloggingPage";
import ProfileEditPage from "pages/ProfileEditPage";
import ProfilePage from "pages/ProfilePage";
import RankingPage from "pages/RankingPage";
import VolunteerRegisterPage from "pages/VolunteerRegisterPage";

const RouteComponent = () => {
  return (
    <Routes>
      <Route></Route>
      <Route path="/*" element={<PageNotFound404 />}></Route>
    </Routes>
  );
};

export default RouteComponent;
