import React, { useEffect } from "react";
import { useRef } from "react";
import { login } from "api/lib/auth";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { userActions } from "store/user-slice";
import { getProfile } from "api/lib/members";
import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
// import { requestPermission } from "firebase-messaging-sw";

function requestPermission() {
  const config = {
    apiKey: process.env.REACT_APP_API_KEY,
    authDomain: "plonit.firebaseapp.com",
    projectId: "plonit",
    storageBucket: "plonit.appspot.com",
    messagingSenderId: process.env.REACT_APP_MESSAGING_SENDER_ID,
    appId: process.env.REACT_APP_APP_ID,
    measurementId: process.env.REACT_APP_MEASUREMENT_ID,
  };

  return navigator.serviceWorker
    .register("./firebase-messaging-sw.js")
    .then(function () {
      void Notification.requestPermission().then((permission) => {
        if (permission === "granted") {
          console.log("푸시 알림이 허용되었습니다.");
        } else {
          console.log("푸시 알림이 허용되지 않았습니다");
        }
      });

      const app = initializeApp(config);
      const messaging = getMessaging(app);

      return getToken(messaging, {
        vapidKey:
          "BI22DGeYupjm6S_19aO8XMQnZD_8o22SfACFvaGUz7pLuxVZ5nlmce4XDXgNoCTsYe18-HER_Y0vyyftyHXvjvE",
      }).catch(() => null);
      // .then((token) => {
      //   if (token.length > 0) {
      //     console.log("푸시 토큰 : ", token);
      //   } else {
      //     console.log("푸시 토큰 실패 !");
      //   }
      // });

      // //포그라운드 메시지 수신
      onMessage(messaging, (payload) => {
        console.log("Message received. ", payload);
        // ...
      });
    });
}

const KakaoCallback = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const code = new URL(window.location.href).searchParams.get("code");
  const Ref = useRef(false);

  useEffect(() => {
    if (!Ref.current) {
      if (code) {
        requestPermission()
          .then((token) => {
            // 토큰을 Promise로 받아옴
            login(
              code,
              token,
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
          })
          .catch((error) => {
            console.log("푸시 토큰 요청 실패:", error); // 토큰 요청 실패시 에러 처리
          });
      }
    }
    return () => {
      Ref.current = true;
    };
  }, []);

  return <div>로그인 로딩 중....</div>;
};

export default KakaoCallback;
