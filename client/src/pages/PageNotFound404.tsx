import React, { useEffect, useRef } from "react";
import Swal from "sweetalert2";

import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { rootState } from "store/store";

const PageNotFound404 = () => {
  const navigate = useNavigate();
  const isLogin = useSelector<rootState, boolean>((state) => {
    return state.user.auth.isLogin;
  });
  const check = useRef<boolean>(false);

  useEffect(() => {
    if (!check.current) {
      const Toast = Swal.mixin({
        toast: true,
        position: "top",
        showConfirmButton: true,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
          toast.onmouseover = Swal.stopTimer;
          toast.onmouseleave = Swal.resumeTimer;
        },
        willClose: () => {
          if (!check.current) {
            navigate("/");
          }
        },
      });

      Toast.fire({
        icon: "error",
        title: `${
          isLogin
            ? "존재하지 않는 페이지입니다."
            : "접근 권한이 없는 페이지입니다."
        }`,
      });
    }

    return () => {
      check.current = true;
    };
  }, []);

  return (
    <div
      style={{
        height: `calc(100% - 56px)`,
        width: "100%",
        backgroundSize: "contain",
        backgroundRepeat: "no-repeat",
        backgroundPosition: "center",
        backgroundImage: `url("/PageNotFound404.jpg")`,
      }}
    ></div>
  );
};

export default PageNotFound404;
