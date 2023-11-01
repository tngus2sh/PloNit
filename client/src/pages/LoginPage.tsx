import React from "react";

const LoginPage = () => {
  const client_id = process.env.REACT_APP_CLIENT_ID;
  const redirect_uri = process.env.REACT_APP_REDIRECT_URI;
  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${client_id}&redirect_uri=${redirect_uri}&response_type=code`;
  const handleLogin = () => {
    window.location.href = KAKAO_AUTH_URL;
  };
  return (
    <div>
      <div onClick={handleLogin}>
        <img src="/kakao_login.png" alt="카카오" style={{ width: "90%" }} />
      </div>
    </div>
  );
};

export default LoginPage;
