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
  // console.log(fcmToken);
  const code = new URL(window.location.href).searchParams.get("code");
  const Ref = useRef(false);

  useEffect(() => {
    if (!Ref.current) {
      if (code) {
        login(
          code,
          (res) => {
            // console.log("로그인 api 성공");
            // console.log(res.data);
            const data = {
              accessToken: res.headers.accesstoken,
              refreshToken: res.headers.refreshtoken,
            };
            // console.log(data);
            dispatch(userActions.loginHandler(data));
            if (res.data.resultBody.registeredMember) {
              getProfile(
                data.accessToken,
                (res) => {
                  // console.log("내 정보 조회 성공");
                  // console.log(res.data.resultBody);
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

  return (
    <div>
      <img src="/loading.gif" alt="로딩중" style={{ width: "90%" }} />
    </div>
  );
};

export default KakaoCallback;
