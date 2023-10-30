import React, { useEffect } from "react";

import { login } from "api/lib/auth";

const KakaoCallback = () => {
  const code = new URL(window.location.href).searchParams.get("code");
  console.log(code);
  useEffect(() => {
    if (code) {
      login(
        code,
        (res) => {
          console.log("로그인 api 성공");
          console.log(res);
          console.log(res.data);
          const accessToken = res.data.accessToken;
          console.log(accessToken);
        },
        (err) => {
          console.error("로그인 api 실패:", err);
        },
      );
    }
  }, []);

  return <div>카카카캌</div>;
};

export default KakaoCallback;
