import React, { useState, useEffect } from "react";
import style from "styles/css/PloggingPage/BottomUpModal.module.css";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

interface IBottomUpModal {
  children?: React.ReactNode;
}

// 스크롤 방지 함수
function disableScroll() {
  // 스크롤 위치 저장
  const scrollX = window.scrollX;
  const scrollY = window.scrollY;

  // 스크롤 위치를 유지하면서 스크롤 막기
  document.body.style.overflow = "hidden";
  window.onscroll = function () {
    window.scrollTo(scrollX, scrollY);
  };
}

// 스크롤 방지 해제 함수
function enableScroll() {
  document.body.style.overflow = "";
  window.onscroll = null;
}

const root = document.documentElement; // HTML 요소 가져오기
const fontSize = parseFloat(
  window.getComputedStyle(root).getPropertyValue("font-size"),
);

const BottomUpModal: React.FC<IBottomUpModal> = ({ children }) => {
  const height = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const width = useSelector<rootState, number>((state) => {
    return state.window.width;
  });
  const [show, setShow] = useState<boolean>(false);
  const className = show
    ? "animate__animated animate__slideInUp"
    : "animate__animated animate__slideOutDown";

  const handleModalClick = (e: React.MouseEvent<HTMLDivElement>) => {
    // 자식 태그를 클릭한 경우, 모달을 닫지 않음
    if (e.target === e.currentTarget) {
      setShow((current) => !current);
    }
  };

  useEffect(() => {
    if (show) {
      disableScroll();
    } else {
      enableScroll();
    }
  }, [show]);

  return (
    <>
      {show && (
        <div
          className={style.bg}
          style={{
            height: height,
            width: Math.min(width, 500),
          }}
          onClick={handleModalClick}
        ></div>
      )}

      <div
        style={{
          height: height,
          width: Math.min(width, 500),
        }}
      >
        <div
          className={className}
          style={{
            position: "fixed",
            boxSizing: "border-box",
            zIndex: 3000,
            width: `${Math.min(width, 500) - 0.5 * fontSize}px`,
            backgroundColor: "white",
            borderRadius: "3px",
            bottom: `0.25rem`,
            padding: `1rem 1.25rem`,
            margin: `0 0.25rem`,
          }}
        >
          {children}
        </div>
      </div>
    </>
  );
};

export default BottomUpModal;
