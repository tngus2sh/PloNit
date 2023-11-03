import React from "react";
import useGPS from "../functions/useGPS";
import CommonButton from "components/common/CommonButton";
import style from "styles/css/PloggingPage/PopUp.module.css";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as camera from "store/camera-slice";

interface IPopUP {
  CameraDivHeight: number;
  handleImageCapture: () => void;
  setShow: (value: boolean) => void;
}

const maxContextLen = 500;

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
      <div>
        <h2 style={{ margin: `0 0` }}>도움 요청하기</h2>
      </div>
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
                  backgroundImage: `url("/images/PloggingPage/camera-solid.svg")`,
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
                top: "2rem",
                right: "0.5rem",
              }}
              onClick={() => {
                dispatch(camera.setImage(""));
              }}
            />
          </div>
        )}
      </div>
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

export default PopUp;
