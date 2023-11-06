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
    const { distance } = state.plogging;
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
    const { calorie } = state.plogging;
    return calorie;
  });
  const isOnWrite = useSelector<rootState, boolean>((state) => {
    return state.camera.isOnWrite;
  });
  const nowType = useSelector<rootState, ploggingType>((state) => {
    return state.plogging.ploggingType;
  });

  function helpBtnEvent() {
    dispatch(camera.setTarget("help"));
    setPreventShow(true);
    setShow(true);
  }
  function stopBtnEvent() {
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
          // axios 요청 성공 시
          Swal.close();
          dispatch(P.setIsEnd(true));
          dispatch(camera.clear());
          navigate("/plogging/complete");
        }
      });
    } else {
      Swal.fire({
        icon: "question",
        html: renderToString(
          <div>
            <div>플로깅을 종료하시겠습니까?</div>
            <div>종료 시 사진 촬영을 진행합니다.</div>
          </div>,
        ),
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#2CD261",
        cancelButtonColor: "#FF2953",
      }).then((result) => {
        if (result.isConfirmed) {
          // axios 요청 성공 시
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
          icon="/images/PloggingPage/stop-green.svg"
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
