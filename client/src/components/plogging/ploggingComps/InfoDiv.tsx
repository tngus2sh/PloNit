import React, { useState, useEffect } from "react";
import style from "styles/css/PloggingPage/InfoDiv.module.css";
import Swal from "sweetalert2";
import useCamera from "../functions/useCamera";
import useGPS from "../functions/useGPS";
import { useNavigate } from "react-router-dom";
import CommonButton from "components/common/CommonButton";
import BottomUpModal from "./BottomUpModal";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";
import * as camera from "store/camera-slice";

interface IInfoTop {
  infoLabel: string;
  infoValue: number | string;
}

interface IIconBottom {
  icon: string;
  backgroundSize: string;
  onClick?: () => void;
}

interface IPopUP {
  CameraDivHeight: number;
  handleImageCapture: () => void;
  setShow: (value: boolean) => void;
}

const maxContextLen = 500;

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

const PopUp: React.FC<IPopUP> = ({
  CameraDivHeight,
  handleImageCapture,
  setShow,
}) => {
  const dispatch = useDispatch();
  const value = useSelector<rootState, string>((state) => {
    return state.camera.value;
  });
  const context = useSelector<rootState, string>((state) => {
    return state.camera.helpContext;
  });
  const { longitude, latitude } = useGPS();

  return (
    <div>
      <div
        className={style.CameraDiv}
        style={{ height: `${CameraDivHeight}px`, width: "100%" }}
      >
        {!value ? (
          <div
            style={{ height: "100%", width: "100%", cursor: "pointer" }}
            onClick={() => {
              handleImageCapture();
            }}
          >
            <div
              style={{
                height: "40%",
                display: "flex",
                justifyContent: "center",
                alignItems: "flex-end",
              }}
            >
              <div
                className={style.camera_image}
                style={{
                  backgroundImage: `url("images/PloggingPage/camera-solid.svg")`,
                  height: "40%",
                  aspectRatio: "1/1",
                }}
              ></div>
            </div>
            <div style={{ height: "60%", fontSize: "1rem" }}>
              <div>{`쓰레기가 많은 구간을 찍어 등록하면`}</div>
              <div>{`주변의 유저들에게 도움을 요청할 수`}</div>
              <div>{` 있습니다.`}</div>
            </div>
          </div>
        ) : (
          <div
            style={{
              height: "100%",
              width: "100%",
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <img
              src={value}
              alt="helpImage"
              style={{ maxHeight: "100%", maxWidth: "100%" }}
              onClick={() => {
                handleImageCapture();
              }}
            />
            <img
              src="images/PloggingPage/delete-button.svg"
              alt="delete-button"
              style={{
                maxWidth: "10%",
                aspectRatio: "1/1",
                position: "fixed",
                cursor: "pointer",
                top: "0.5rem",
                right: "0.5rem",
              }}
              onClick={() => {
                dispatch(camera.setImage(""));
              }}
            />
          </div>
        )}
      </div>
      <h2 style={{ margin: `0 0` }}>도움 요청하기</h2>
      <br />
      <div
        className={style.ContextDiv}
        style={{
          height: `${CameraDivHeight}px`,
          width: "100%",
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <textarea
          style={{ height: "90%", width: "95%" }}
          placeholder="다른 유저들이 도와주러 올 수 있도록 간단한 설명을 기록해주세요."
          value={context}
          id="helpContext"
          onChange={(event) => {
            const { value } = event.target;
            if (value.length <= maxContextLen) {
              dispatch(camera.setHelpContext(value));
            }
          }}
        />
      </div>
      <CommonButton
        text="등록하기"
        id="InfoDiv-CommonBtn"
        styles={{
          backgroundColor: "#2cd261",
        }}
        onClick={() => {
          async function saveHelpWithImage() {
            if (value) {
              const blob = await fetch(value).then((response) => {
                return response.blob();
              });
              if (blob) {
                const jpgFile = new File([blob], "image.jpg", {
                  type: "image/jpeg",
                });
                console.log(jpgFile);
                dispatch(camera.clear());
                setShow(false);
              }
            }
          }

          if (value) {
            saveHelpWithImage();
            // console.log("longitude", longitude);
            // console.log("latitude", latitude);
          } else {
            console.log("no - image");
          }
        }}
      />
    </div>
  );
};

const InfoDiv = ({ infoDivHeight }: { infoDivHeight: number }) => {
  const { image, handleImageCapture, fileInputRef } = useCamera();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
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

  const [show, setShow] = useState<boolean>(false);
  const [preventShow, setPreventShow] = useState<boolean>(false);

  function helpBtnEvent() {
    dispatch(camera.setTarget("help"));
    setPreventShow(true);
    setShow(true);
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
        dispatch(camera.clear());
        navigate("/plogging/complete");
      }
    });
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

  // 이미지가 로드되었을 때, 이미지를 넘겨준다.
  useEffect(() => {
    if (image) {
      dispatch(camera.setImage(image));
      navigate("/plogging/image");
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
          onClick={() => {
            helpBtnEvent();
          }}
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
      {preventShow && (
        <BottomUpModal show={show} setShow={setShow}>
          <PopUp
            CameraDivHeight={windowHeight * 0.25}
            handleImageCapture={handleImageCapture}
            setShow={setShow}
          />
        </BottomUpModal>
      )}
    </div>
  );
};

export default InfoDiv;
