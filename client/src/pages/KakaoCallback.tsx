import React, { useState, useEffect } from "react";
import { useRef } from "react";
import { login } from "api/lib/auth";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { userActions } from "store/user-slice";
import { getProfile } from "api/lib/members";
import { NotOkModal } from "components/common/AlertModals";

const KakaoCallback = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const fcmToken = useSelector((state: any) => state.user.alarm.fcmToken);
  console.log(fcmToken);
  const code = new URL(window.location.href).searchParams.get("code");
  const Ref = useRef(false);

  useEffect(() => {
    if (!Ref.current) {
      if (code) {
        login(
          code,
          fcmToken,
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
            NotOkModal({ text: "로그인이 실패했습니다." });
          },
        );
      }
    }
    return () => {
      Ref.current = true;
    };
  }, []);

  // useEffect(() => {
  //   if (!Ref.current) {
  //     if (code) {
  //       requestPermission()
  //         .then((token) => {
  //           // 토큰을 Promise로 받아옴
  //           login(
  //             code,
  //             token,
  //             (res) => {
  //               console.log("로그인 api 성공");
  //               console.log(res.data);
  //               const data = {
  //                 accessToken: res.headers.accesstoken,
  //                 refreshToken: res.headers.refreshtoken,
  //               };
  //               console.log(data);
  //               dispatch(userActions.loginHandler(data));
  //               if (res.data.resultBody.registeredMember) {
  //                 getProfile(
  //                   data.accessToken,
  //                   (res) => {
  //                     console.log("내 정보 조회 성공");
  //                     console.log(res.data.resultBody);
  //                     dispatch(userActions.saveMemberInfo(res.data.resultBody));
  //                   },
  //                   (err) => {
  //                     console.log("내 정보 조회 실패", err);
  //                   },
  //                 );
  //                 navigate("/");
  //               } else {
  //                 navigate("/login/addinfo");
  //               }
  //             },
  //             (err) => {
  //               console.error("로그인 api 실패:", err);
  //             },
  //           );
  //         })
  //         .catch((error) => {
  //           console.log("푸시 토큰 요청 실패:", error); // 토큰 요청 실패시 에러 처리
  //         });
  //     }
  //   }
  //   return () => {
  //     Ref.current = true;
  //   };
  // }, []);

  return <div>로그인 로딩 중....</div>;
};

export default KakaoCallback;
