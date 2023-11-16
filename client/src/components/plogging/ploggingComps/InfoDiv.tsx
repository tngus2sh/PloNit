import React, { useEffect } from "react";
import style from "styles/css/PloggingPage/InfoDiv.module.css";
import Swal from "sweetalert2";
import PloggingInfo from "./PloggingInfo";
import { ploggingType } from "types/ploggingTypes";
import { renderToString } from "react-dom/server";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";
import * as Crewping from "store/crewping-slice";
import * as camera from "store/camera-slice";

interface IIconBottom {
  icon: string;
  backgroundSize: string;
  onClick?: () => void;
}

interface IInfoDiv {
  infoDivHeight: number;
  setShow: (value: boolean) => void;
  setPreventShow: (value: boolean) => void;
  handleImageCapture: () => void;
}

function formatNumber(n: number): string {
  if (n < 10) {
    return "0" + n;
  }

  return n.toString();
}

const IconBottom: React.FC<IIconBottom> = ({
  icon,
  backgroundSize,
  onClick,
}) => {
  return (
    <div className={style.IconBottom}>
      <div
        className={style.icon}
        style={{
          backgroundImage: `url("${icon}")`,
          backgroundSize: backgroundSize,
          transition: `all 200ms ease-in-out`,
        }}
        onClick={onClick}
      ></div>
    </div>
  );
};

const InfoDiv: React.FC<IInfoDiv> = ({
  infoDivHeight,
  setShow,
  setPreventShow,
  handleImageCapture,
}) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const distance = useSelector<rootState, number>((state) => {
    const distance = state.plogging.distance / 1000;
    return Math.round(distance * 100) / 100;
  });
  const second = useSelector<rootState, number>((state) => {
    const { second } = state.plogging;
    return second;
  });
  const minute = useSelector<rootState, number>((state) => {
    const { minute } = state.plogging;
    return minute;
  });
  const calorie = useSelector<rootState, number>((state) => {
    const calorie = state.plogging.calorie;
    return Math.round(calorie * 10) / 10;
  });
  const isOnWrite = useSelector<rootState, boolean>((state) => {
    return state.camera.isOnWrite;
  });
  const nowType = useSelector<rootState, ploggingType>((state) => {
    return state.plogging.ploggingType;
  });
  const isLoading = useSelector<rootState, number>((state) => {
    return state.plogging.isLoading;
  });
  const imagesLen = useSelector<rootState, number>((state) => {
    return state.plogging.images.length;
  });
  const charge = useSelector<rootState, boolean>((state) => {
    return state.crewping.charge;
  });

  function helpBtnEvent() {
    dispatch(camera.setTarget("help"));
    setPreventShow(true);
    setShow(true);
  }
  function stopBtnEvent() {
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
    if (nowType !== "VOL") {
      Swal.fire({
        icon: "question",
        text: "플로깅을 종료하시겠습니까?",
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#2CD261",
        cancelButtonColor: "#FF2953",
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.close();
          if (nowType === "IND") {
            dispatch(P.setIsEnd(true));
            dispatch(camera.clear());
            navigate("/plogging/complete");
          } else {
            if (charge) {
              dispatch(Crewping.setEndRequest(true));
              dispatch(camera.clear());
            } else {
              dispatch(Crewping.setCrewpingEnd(true));
              dispatch(camera.clear());
            }
          }
        }
      });
    } else {
      Swal.fire({
        icon: "question",
        html: renderToString(
          <div>
            <div>
              {imagesLen < 2
                ? `봉사 인증을 위해선 3장 이상의 사진이 필요합니다.`
                : `플로깅을 종료하시겠습니까?`}
            </div>
            <br />
            {imagesLen < 2 && <div>현재 촬영된 이미지 수: {imagesLen}</div>}
            <div style={{ fontWeight: "bolder" }}>
              종료 시 사진 촬영을 진행합니다.
            </div>
          </div>,
        ),
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#2CD261",
        cancelButtonColor: "#FF2953",
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.close();
          dispatch(P.setBeforeEnd(true));
          dispatch(camera.clear());
          navigate("/plogging/complete");
        }
      });
    }
  }
  function CameraBtnEvent() {
    dispatch(camera.setTarget("save"));
    handleImageCapture();
  }

  useEffect(() => {
    if (isOnWrite) {
      setPreventShow(true);
      setShow(true);
    }
  }, []);

  return (
    <div style={{ height: `${infoDivHeight}px`, width: "100%" }}>
      <div style={{ height: "10%", width: "100%" }}></div>
      <div style={{ height: "40%", width: "100%", display: "flex" }}>
        <PloggingInfo infoLabel="km" infoValue={distance} />
        <PloggingInfo
          infoLabel="시간"
          infoValue={`${minute}:${formatNumber(second)}`}
        />
        <PloggingInfo infoLabel="칼로리" infoValue={calorie} />
      </div>
      <div style={{ height: "50%", width: "100%", display: "flex" }}>
        <IconBottom
          icon="/images/PloggingPage/help-solid.svg"
          backgroundSize="50%"
          onClick={() => {
            helpBtnEvent();
          }}
        />
        <IconBottom
          icon={
            isLoading > 0
              ? "/images/PloggingPage/stop-gray.svg"
              : "/images/PloggingPage/stop-green.svg"
          }
          backgroundSize="contain"
          onClick={stopBtnEvent}
        />
        <IconBottom
          icon="/images/PloggingPage/camera-solid.svg"
          backgroundSize="50%"
          onClick={CameraBtnEvent}
        />
      </div>
    </div>
  );
};

export default InfoDiv;
