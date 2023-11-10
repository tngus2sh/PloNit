import React, { useEffect } from "react";
import { useRef } from "react";
import { login } from "api/lib/auth";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { userActions } from "store/user-slice";

const KakaoCallback = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const code = new URL(window.location.href).searchParams.get("code");
  const Ref = useRef(false);
  console.log(code);
  useEffect(() => {
    if (!Ref.current) {
      if (code) {
        login(
          code,
          (res) => {
            console.log("로그인 api 성공");
            console.log(res);
            console.log(res.data);
            const data = {
              accessToken: res.headers.accesstoken,
              refreshToken: res.headers.refreshtoken,
              // profileImg: res.data.resultBody.profileImage,
              nickname: res.data.resultBody.nickname,
            };
            console.log(data);
            dispatch(userActions.loginHandler(data));
            console.log(res);
            console.log(res.data);
            if (res.data.resultBody.registeredMember) {
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
    return () => {
      Ref.current = true;
    };
  }, []);

  return <div>로그인 로딩 중....</div>;
};

export default KakaoCallback;
