import React, { useState, useEffect, useRef } from "react";
import useCamera from "../functions/useCamera";
import CommonButton from "components/common/CommonButton";
import Input from "components/common/Input";
import { BasicTopBar } from "components/common/TopBar";
import style from "styles/css/PloggingPage/PloggingVolunteerInput.module.css";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";
import * as camera from "store/camera-slice";

const PloggingVolunteerInput = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const windowWidth = useSelector<rootState, number>((state) => {
    return state.window.width;
  });
  const isVolEnd = useSelector<rootState, boolean>((state) => {
    return state.plogging.isVolEnd;
  });

  const { image, handleImageCapture, fileInputRef } = useCamera();
  const check = useRef<boolean>(false);
  const [name, setName] = useState<string>("");
  const [phoneNumber, setPhoneNumber] = useState<string>(""); // 휴대폰 번호 양식을 만들어야 한다.
  const [id_1365, setId_1365] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [region, setRegion] = useState<string>("");

  useEffect(() => {
    if (!check.current && !isVolEnd) {
      handleImageCapture();
    }

    return () => {
      if (!check.current && !isVolEnd) {
        check.current = true;
        dispatch(P.setIsVolEnd(true));
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
        id="cameraInput-VOL-COMPLETE"
        ref={fileInputRef}
        style={{ display: "none" }}
      />
      <BasicTopBar text="봉사 플로깅 정보 입력" styles={{ zIndex: "1000" }} />
      <div
        style={{
          padding: "0 5%",
          boxSizing: "border-box",
        }}
      >
        <Input
          type="string"
          labelTitle="이름"
          styles={{
            marginLeft: "0",
            marginRight: "0",
          }}
        />
        <Input
          type="string"
          labelTitle="연락처"
          styles={{
            marginLeft: "0",
            marginRight: "0",
          }}
        />
        <Input
          type="string"
          labelTitle="1365 ID"
          styles={{
            marginLeft: "0",
            marginRight: "0",
          }}
        />
        <Input
          type="string"
          labelTitle="이메일"
          styles={{
            marginLeft: "0",
            marginRight: "0",
          }}
        />
      </div>
    </div>
  );
};

export default PloggingVolunteerInput;
