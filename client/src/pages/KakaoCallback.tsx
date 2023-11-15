import React, { useState, useEffect } from "react";
import { useRef } from "react";
import { login } from "api/lib/auth";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { userActions } from "store/user-slice";
import { getProfile } from "api/lib/members";
import { NotOkModal } from "components/common/AlertModals";
// import { requestPermission } from "firebase-messaging-sw";
import { registerServiceWorker } from "notification";
import { messaging } from "settingFCM";
import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

async function handleAllowNotification() {
  const permission = await Notification.requestPermission();
  registerServiceWorker();
}

const KakaoCallback = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const code = new URL(window.location.href).searchParams.get("code");
  const Ref = useRef(false);
  const [deviceToken, setDeviceToken] = useState("");
  const getDeviceToken = async () => {
    try {
      const token = await getToken(messaging, {
        vapidKey: process.env.REACT_APP_VAPID_KEY,
      });
      setDeviceToken(token);
    } catch (error) {
      console.error("디바이스 토큰 발급 실패:", error);
      setDeviceToken("");
    }
  };
  getDeviceToken();
  console.log(deviceToken);

  useEffect(() => {
    const fetchData = async () => {
      if (!Ref.current) {
        if (code) {
          await getDeviceToken();
          if (deviceToken !== "") {
            login(
              code,
              deviceToken,
              (res) => {
                console.log("로그인 api 성공");
                console.log(res.data);
                const data = {
                  accessToken: res.headers.accesstoken,
                  refreshToken: res.headers.refreshtoken,
                };
                console.log(data);
                dispatch(userActions.loginHandler(data));
                if (res.data.resultBody.registeredMember) {
                  getProfile(
                    data.accessToken,
                    (res) => {
                      console.log("내 정보 조회 성공");
                      console.log(res.data.resultBody);
                      dispatch(userActions.saveMemberInfo(res.data.resultBody));
                    },
                    (err) => {
                      console.log("내 정보 조회 실패", err);
                    },
                  );
                  navigate("/");
                } else {
                  navigate("/login/addinfo");
                }
              },
              (err) => {
                console.error("로그인 api 실패:", err);
              },
            );
          } else {
            login(
              code,
              "",
              (res) => {
                console.log("로그인 api 성공");
                console.log(res.data);
                const data = {
                  accessToken: res.headers.accesstoken,
                  refreshToken: res.headers.refreshtoken,
                };
                console.log(data);
                dispatch(userActions.loginHandler(data));
                if (res.data.resultBody.registeredMember) {
                  getProfile(
                    data.accessToken,
                    (res) => {
                      console.log("내 정보 조회 성공");
                      console.log(res.data.resultBody);
                      dispatch(userActions.saveMemberInfo(res.data.resultBody));
                    },
                    (err) => {
                      console.log("내 정보 조회 실패", err);
                    },
                  );
                  navigate("/");
                } else {
                  navigate("/login/addinfo");
                }
              },
              (err) => {
                console.error("로그인 api 실패:", err);
              },
            );
          }
        }
      }
    };

    fetchData();

    return () => {
      Ref.current = true;
    };
  }, [deviceToken]);

  return <div>로그인 로딩 중....</div>;
};

export default KakaoCallback;
