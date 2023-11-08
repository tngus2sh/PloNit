import React, { useState, useEffect, useRef } from "react";
import DefaultMap from "../DefaultMap";
import InfoDiv from "../ploggingComps/InfoDiv";
import useCamera from "../functions/useCamera";
import PopUp from "../ploggingComps/PopUp";
import BottomUpModal from "../ploggingComps/BottomUpModal";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as camera from "store/camera-slice";

interface member {
  image: string;
  name: string;
}

const Crewping = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const infoDivHeight = useSelector<rootState, number>((state) => {
    const windowHeight = state.window.height;
    return windowHeight * 0.25;
  });
  const isOnWrite = useSelector<rootState, boolean>((state) => {
    return state.camera.isOnWrite;
  });
  const beforeCrewping = useSelector<rootState, boolean>((state) => {
    return state.plogging.beforeCrewping;
  });
  const [show, setShow] = useState<boolean>(false);
  const [preventShow, setPreventShow] = useState<boolean>(false);
  const [members, setMembers] = useState<member[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const { image, handleImageCapture, fileInputRef } = useCamera();
  const check1 = useRef<boolean>(false);
  const check2 = useRef<boolean>(false);

  const accessToken = useSelector<rootState, string>((state) => {
    return state.user.accessToken;
  });

  useEffect(() => {
    if (!check1.current) {
      if (isOnWrite) {
        setPreventShow(true);
        setShow(true);
        dispatch(camera.setIsOnWrite(false));
      }
      if (beforeCrewping) {
        // 모달이 나와야 하며, 그를 비롯한 정보들이 나와야 한다.
      }
    }

    return () => {
      if (!check1.current) {
        check1.current = true;
      }
    };
  }, []);

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
        accept="image/*"
        capture="environment"
        id="cameraInput-CREWPING"
        ref={fileInputRef}
        style={{ display: "none" }}
      />
    </>
  );
};

export default Crewping;
