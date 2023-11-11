import React, { useEffect, useState } from "react";
import Swal from "sweetalert2";
import DefaultPathMap from "../DefaultPathMap";
import { BasicTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
import PloggingInfo from "../ploggingComps/PloggingInfo";
import PloggingSwiper from "../ploggingComps/PloggingSwiper";
import useCamera from "../functions/useCamera";
import { Coordinate } from "interface/ploggingInterface";
import { ploggingType } from "types/ploggingTypes";
import style from "styles/css/PloggingPage/PloggingComplete.module.css";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as camera from "store/camera-slice";
import * as P from "store/plogging-slice";

import { savePlogging } from "api/lib/plogging";

function formatNumber(n: number): string {
  if (n < 10) {
    return "0" + n;
  }

  return n.toString();
}

const maxContextLen = 500;

const PloggingComplete = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const windowWidth = useSelector<rootState, number>((state) => {
    return state.window.width;
  });
  const paths = useSelector<rootState, Coordinate[]>((state) => {
    return state.plogging.paths;
  });
  const distance = useSelector<rootState, number>((state) => {
    const distance = state.plogging.distance / 1000;
    return Math.round(distance * 100) / 100;
  });
  const minute = useSelector<rootState, number>((state) => {
    return state.plogging.minute;
  });
  const second = useSelector<rootState, number>((state) => {
    return state.plogging.second;
  });
  const calorie = useSelector<rootState, number>((state) => {
    const calorie = state.plogging.calorie;
    return Math.round(calorie * 10) / 10;
  });
  const images = useSelector<rootState, string[]>((state) => {
    return state.plogging.images;
  });

  const accessToken = useSelector<rootState, string>((state) => {
    return state.user.auth.accessToken;
  });
  const nowType = useSelector<rootState, ploggingType>((state) => {
    return state.plogging.ploggingType;
  });
  const ploggingId = useSelector<rootState, number>((state) => {
    return state.plogging.ploggingId;
  });
  const crewpingId = useSelector<rootState, number>((state) => {
    return state.plogging.crewpingId;
  });
  const people = useSelector<rootState, number | null>((state) => {
    return state.plogging.people;
  });
  const coordinates = useSelector<rootState, Coordinate[]>((state) => {
    return state.plogging.paths;
  });
  const isLoading = useSelector<rootState, number>((state) => {
    return state.plogging.isLoading;
  });
  const [context, setContext] = useState<string>("");

  const time = new Date();
  const year = time.getFullYear();
  const month = time.getMonth() + 1;
  const date = time.getDate();
  const day = ["일", "월", "화", "수", "목", "금", "토"][time.getDay()];
  const { image, handleImageCapture, fileInputRef } = useCamera();

  useEffect(() => {
    if (image) {
      dispatch(camera.setImage(image));
      navigate("/plogging/image");
    }
  }, [image]);

  return (
    <div
      style={{
        width: "100%",
        height: `${windowHeight - 56}px`,
        overflowY: "scroll",
      }}
    >
      <input
        type="file"
        accept="image/*"
        capture="environment"
        id="cameraInput-COMPLETE"
        ref={fileInputRef}
        style={{ display: "none" }}
      />
      <BasicTopBar text="플로깅 완료" styles={{ zIndex: "1000" }} />
      <div
        style={{
          padding: "0 5%",
          boxSizing: "border-box",
        }}
      >
        <div className={style.text}>
          <div>오늘도</div>
          <div>플로깅 완료!!</div>
        </div>
        <div
          style={{
            height: "auto",
            width: "100%",
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <DefaultPathMap paths={paths} subHeight={0} />
        </div>
        <div className={style.text}>
          <div>{`${year}년`}</div>
          <div>{`${month}월 ${formatNumber(date)}일 ${day}요일`}</div>
        </div>
        <div
          style={{
            width: "100%",
            height: windowHeight * 0.0625,
            display: "flex",
          }}
        >
          <PloggingInfo infoLabel="km" infoValue={distance} />
          <PloggingInfo
            infoLabel="시간"
            infoValue={`${minute}:${formatNumber(second)}`}
          />
          <PloggingInfo infoLabel="칼로리" infoValue={calorie} />
        </div>
        <br />
        <div className={style.text_sm}>{`플로깅 사진`}</div>
        <PloggingSwiper
          images={images}
          onClick={() => {
            handleImageCapture();
          }}
        />
        <div className={style.text_sm}>{`플로깅 기록`}</div>
        <div
          className={style.textarea_wrapper}
          style={{ height: windowHeight * 0.3, width: "100%" }}
        >
          <textarea
            id="plogging-text"
            style={{ height: "100%", width: "98%" }}
            placeholder="오늘의 플로깅을 기록해주세요."
            value={context}
            onChange={(event) => {
              const { value } = event.target;
              if (value.length <= maxContextLen) {
                setContext(value);
              }
            }}
          />
        </div>
        <div style={{ height: "5rem" }}></div>
      </div>
      <CommonButton
        text="완료"
        styles={{
          width: `${Math.min(500 - windowHeight * 0.05, windowWidth * 0.9)}px`,
          backgroundColor: `${isLoading > 0 ? "#999999" : "#2cd261"}`,
          position: "fixed",
          bottom: "56px",
          zIndex: "1000",
          boxSizing: "border-box",
          margin: `5px ${windowHeight * 0.025}px`,
          transition: `all 200ms ease-in-out`,
        }}
        onClick={() => {
          if (isLoading > 0) {
            const Toast = Swal.mixin({
              toast: true,
              position: "top",
              showConfirmButton: false,
              timer: 2500,
              timerProgressBar: true,
              didOpen: (toast) => {
                toast.onmouseover = Swal.stopTimer;
                toast.onmouseleave = Swal.resumeTimer;
              },
            });
            Toast.fire({
              icon: "info",
              title: "이미지 전송 중입니다...",
            });
            return;
          }

          Swal.fire({
            icon: "question",
            text: "작성을 종료하시겠습니까?",
            showCancelButton: true,
            confirmButtonText: "예",
            cancelButtonText: "아니오",
            confirmButtonColor: "#2CD261",
            cancelButtonColor: "#FF2953",
          }).then((result) => {
            if (result.isConfirmed) {
              if (nowType === "CREWPING") {
                savePlogging({
                  accessToken: accessToken,
                  crewpingId: crewpingId,
                  distance: distance,
                  calorie: calorie,
                  review: context,
                  people: people,
                  coordinates: coordinates,
                  success: (response) => {
                    console.log(response);
                    dispatch(camera.clear());
                    dispatch(P.clear());
                    navigate("/");
                  },
                  fail: (error) => {
                    console.error(error);
                  },
                });
              } else if (nowType === "IND" || nowType === "VOL") {
                savePlogging({
                  accessToken: accessToken,
                  ploggingId: ploggingId,
                  distance: distance,
                  calorie: calorie,
                  review: context,
                  coordinates: coordinates,
                  success: (response) => {
                    console.log(response);
                    dispatch(camera.clear());
                    dispatch(P.clear());
                    navigate("/");
                  },
                  fail: (error) => {
                    console.error(error);
                  },
                });
              }
            }
          });
        }}
      />
    </div>
  );
};

export default PloggingComplete;
