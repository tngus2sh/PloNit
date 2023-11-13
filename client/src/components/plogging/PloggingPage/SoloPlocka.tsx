import React, { useState, useEffect, useRef } from "react";
import DefaultMap from "../DefaultMap";
import InfoDiv from "../ploggingComps/InfoDiv";
import useCamera from "../functions/useCamera";
import PopUp from "../ploggingComps/PopUp";
import BottomUpModal from "../ploggingComps/BottomUpModal";
import Swal from "sweetalert2";
import { renderToString } from "react-dom/server";
import style from "styles/css/PloggingPage/SoloPlocka.module.css";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as camera from "store/camera-slice";
import * as P from "store/plogging-slice";

const FirstPopUp = () => {
  return (
    <div className={style.First_PopUp}>
      <div className={style.blue_top}>
        <div>{`앱을 사용하는 동안 위치권한`}</div>
        <div>{`및 사진 권한이필요합니다.`}</div>
      </div>
      <div className={style.text}>
        <div>{`사용자의 이동경로, 촬영된 사진은`}</div>
        <div>{`봉사활동 인증에만 사용됩니다.`}</div>
      </div>
      <hr />
      <div className={style.black_bottom}>{`시작 사진 촬영`}</div>
      <div className={style.text}>
        <div>{`시작 전 빈봉투 사진을`}</div>
        <div>{`찍어주세요.`}</div>
      </div>
    </div>
  );
};

const SoloPlocka = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const infoDivHeight = useSelector<rootState, number>((state) => {
    const windowHeight = state.window.height;
    return windowHeight * 0.25;
  });
  const isOnWrite = useSelector<rootState, boolean>((state) => {
    return state.camera.isOnWrite;
  });
  const isVolStart = useSelector<rootState, boolean>((state) => {
    return state.plogging.isVolStart;
  });
  const volTakePicture = useSelector<rootState, boolean>((state) => {
    return state.plogging.volTakePicture;
  });
  const isVolTakePicture = useSelector<rootState, boolean>((state) => {
    return state.plogging.isVolTakePicture;
  });

  const [show, setShow] = useState<boolean>(false);
  const [preventShow, setPreventShow] = useState<boolean>(false);
  const { image, handleImageCapture, fileInputRef } = useCamera();
  const check1 = useRef<boolean>(false);
  const check2 = useRef<boolean>(false);

  // 시작 시 사진을 찍는다.
  useEffect(() => {
    if (!check1.current) {
      if (isOnWrite) {
        setPreventShow(true);
        setShow(true);
        dispatch(camera.setIsOnWrite(false));
      }
      if (!isVolStart) {
        Swal.fire({
          html: renderToString(<FirstPopUp />),
          confirmButtonText: "확인",
          confirmButtonColor: "#2CD261",
          didClose: () => {
            handleImageCapture();
          },
        });
      }
    }

    return () => {
      if (!check1.current) {
        check1.current = true;

        if (!isVolStart) {
          dispatch(P.setIsVolStart(true));
        }
      }
    };
  }, []);

  // 30분이 되었을 때, 카메라를 키게 만든다.
  useEffect(() => {
    if (volTakePicture && !isVolTakePicture && !check2.current) {
      dispatch(camera.setTarget("save"));
      handleImageCapture();
    }

    return () => {
      if (volTakePicture && !isVolTakePicture && !check2.current) {
        dispatch(P.setIsVolTakePicture(true));
        check2.current = true;
      }
    };
  }, [volTakePicture]);

  // 이미지가 로드되었을 때, 이미지를 넘겨준다.
  useEffect(() => {
    if (image) {
      dispatch(camera.setImage(image));
      navigate("/plogging/image");
    }
  }, [image]);

  return (
    <>
      <DefaultMap subHeight={infoDivHeight} isBefore={false}>
        <InfoDiv
          infoDivHeight={infoDivHeight}
          setShow={setShow}
          setPreventShow={setPreventShow}
          handleImageCapture={handleImageCapture}
        />
      </DefaultMap>
      {preventShow && (
        <BottomUpModal show={show} setShow={setShow}>
          <PopUp
            CameraDivHeight={infoDivHeight}
            handleImageCapture={handleImageCapture}
            setShow={setShow}
          />
        </BottomUpModal>
      )}
      <input
        type="file"
        accept="image/jpeg, image/png"
        capture="environment"
        id="cameraInput-IND"
        ref={fileInputRef}
        style={{ display: "none" }}
      />
    </>
  );
};

export default SoloPlocka;
