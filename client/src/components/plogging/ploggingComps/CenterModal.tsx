import React, { useEffect } from "react";
import style from "styles/css/PloggingPage/CenterModal.module.css";

interface ICenterModal {
  show: boolean;
  setShow: (value: boolean) => void;
  children?: React.ReactNode;
}

import { useSelector } from "react-redux";
import { rootState } from "store/store";

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

const CenterModal: React.FC<ICenterModal> = ({ show, setShow, children }) => {
  const height = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const width = useSelector<rootState, number>((state) => {
    return state.window.width;
  });
  const handleModalClick = (e: React.MouseEvent<HTMLDivElement>) => {
    // 자식 태그를 클릭한 경우, 모달을 닫지 않음
    if (e.target === e.currentTarget) {
      setShow(false);
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
        <div>
          <div
            className={style.bg}
            style={{
              height: height,
              width: Math.min(width, 500),
            }}
            onClick={handleModalClick}
          ></div>
          <div
            style={{
              position: "fixed",
              boxSizing: "border-box",
              zIndex: 3000,
              width: `${Math.min(width, 500) - 1.5 * fontSize}px`,
              backgroundColor: "white",
              borderRadius: "3px",
              padding: `1.5rem 1.25rem`,
              top: "50%",
              left: "50%",
              transform: "translate(-50%, -50%)",
            }}
          >
            {children}
          </div>
        </div>
      )}
    </>
  );
};

export default CenterModal;
