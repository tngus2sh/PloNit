import React, { useEffect } from "react";
import style from "styles/css/PloggingPage/InfoDiv.module.css";
import Swal from "sweetalert2";
import useCamera from "../functions/useCamera";
import { useNavigate } from "react-router-dom";
import CommonButton from "components/common/CommonButton";
import { renderToString } from "react-dom/server";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";

interface IInfoTop {
  infoLabel: string;
  infoValue: number | string;
}

interface IIconBottom {
  icon: string;
  backgroundSize: string;
  onClick?: () => void;
}

function formatNumber(n: number): string {
  if (n < 10) {
    return "0" + n;
  }

  return n.toString();
}

const InfoTop: React.FC<IInfoTop> = ({ infoLabel, infoValue }) => {
  return (
    <div className={style.InfoTop}>
      <div className={style.up}>{infoLabel}</div>
      <div className={style.down}>{infoValue}</div>
    </div>
  );
};

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

const PopUp: React.FC = ({}) => {
  const CameraDiv = ({ id }: { id?: string }) => {
    return <div className={style.CamerDiv}></div>;
  };
  const MediumDiv = ({ id }: { id?: string }) => {
    return (
      <div className={style.MediumDiv}>
        반경<span></span> 이내 플로퍼들에게 도움 요청하기
      </div>
    );
  };
  const ContextDiv = ({ id }: { id?: string }) => {
    return <div className={style.ContextDiv}></div>;
  };

  return (
    <div>
      <h2 style={{ margin: `0 0` }}>도움 요청하기</h2>
      <CameraDiv />
      <MediumDiv />
      <ContextDiv />
      <CommonButton text="등록하기" id="help-popup-commonBtn" />
    </div>
  );
};

const InfoDiv = ({ infoDivHeight }: { infoDivHeight: number }) => {
  const { image, handleImageCapture, fileInputRef } = useCamera();
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

  function helpBtnEvent() {
    Swal.fire({
      position: "bottom",
      html: renderToString(<PopUp />),
      showConfirmButton: false,
      showClass: {
        popup: "animate__animated animate__slideInUp",
      },
      hideClass: {
        popup: "animate__animated animate__slideOutDown",
      },
      willOpen: () => {
        console.log("옵흔");
      },
    });
  }
  function stopBtnEvent() {
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
        dispatch(P.setPloggingType("none"));
        dispatch(P.setCbURL("/"));
        navigate("/plogging/complete");
      }
    });
  }
  function CameraBtnEvent() {
    handleImageCapture();
  }

  // 이미지가 로드되었을 때, 이미지를 넘겨준다.
  useEffect(() => {
    if (image) {
      navigate("/plogging/image", { state: { value: image } });
    }
  }, [image]);

  return (
    <div style={{ height: `${infoDivHeight}px`, width: "100%" }}>
      <div style={{ height: "10%", width: "100%" }}></div>
      <div style={{ height: "40%", width: "100%", display: "flex" }}>
        <InfoTop infoLabel="km" infoValue={distance} />
        <InfoTop
          infoLabel="시간"
          infoValue={`${minute}:${formatNumber(second)}`}
        />
        <InfoTop infoLabel="칼로리" infoValue={calorie} />
      </div>
      <div style={{ height: "50%", width: "100%", display: "flex" }}>
        <IconBottom
          icon="images/PloggingPage/help-solid.svg"
          backgroundSize="50%"
          onClick={helpBtnEvent}
        />
        <IconBottom
          icon="images/PloggingPage/stop-green.svg"
          backgroundSize="contain"
          onClick={stopBtnEvent}
        />
        <IconBottom
          icon="images/PloggingPage/camera-solid.svg"
          backgroundSize="50%"
          onClick={CameraBtnEvent}
        />
      </div>
      <input
        type="file"
        accept="image/*"
        capture="environment"
        id="cameraInput"
        ref={fileInputRef}
        style={{ display: "none" }}
      />
    </div>
  );
};

export default InfoDiv;
