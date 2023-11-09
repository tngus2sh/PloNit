import React, { useState, useEffect, useRef } from "react";
import useCamera from "../functions/useCamera";
import CommonButton from "components/common/CommonButton";
import Input from "components/common/Input";
import { BasicTopBar } from "components/common/TopBar";
import Swal from "sweetalert2";
import RegionModal from "components/common/RegionModal";
import style from "styles/css/PloggingPage/PloggingVolunteerInput.module.css";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";
import * as camera from "store/camera-slice";

import { registerVolInfo } from "api/lib/plogging";

const PloggingVolunteerInput = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const windowWidth = useSelector<rootState, number>((state) => {
    return state.window.width;
  });
  const reduxName = useSelector<rootState, string>((state) => {
    return state.user.name;
  });
  const reduxEmail = useSelector<rootState, string>((state) => {
    return state.user.email;
  });
  const reduxId1365 = useSelector<rootState, string>((state) => {
    return state.user.id_1365;
  });
  const isVolEnd = useSelector<rootState, boolean>((state) => {
    return state.plogging.isVolEnd;
  });
  const acessToken = useSelector<rootState, string>((state) => {
    return state.user.accessToken;
  });

  const { image, handleImageCapture, fileInputRef } = useCamera();
  const check = useRef<boolean>(false);
  const [name, setName] = useState<string>(reduxName);
  const [id_1365, setId_1365] = useState<string>(reduxId1365);
  const [email, setEmail] = useState<string>(reduxEmail);
  const [region, setRegion] = useState<string>("");
  const [regionCode, setRegionCode] = useState<string>("");
  const [isOpen, setIsOpen] = useState<boolean>(false);

  function handleIsOpen() {
    setIsOpen((current) => !current);
  }

  useEffect(() => {
    if (!check.current) {
      if (!isVolEnd) {
        handleImageCapture();
      }
    }

    return () => {
      if (!check.current) {
        check.current = true;
        if (!isVolEnd) {
          dispatch(P.setIsVolEnd(true));
        }
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
          height: `calc(100% - 7vh)`,
        }}
      >
        <Input
          type="string"
          labelTitle="이름"
          id="vol-name"
          styles={{
            marginLeft: "0",
            marginRight: "0",
          }}
          value={name}
          onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
            const { value } = event.target;
            if (value.length <= 30) {
              setName(value);
            }
          }}
        />
        <Input
          type="string"
          labelTitle="1365 ID"
          id="vol-id_1365"
          styles={{
            marginLeft: "0",
            marginRight: "0",
          }}
          value={id_1365}
          onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
            const { value } = event.target;
            if (value.length <= 30) {
              setId_1365(value);
            }
          }}
        />
        <Input
          type="string"
          labelTitle="이메일"
          id="vol-email"
          styles={{
            marginLeft: "0",
            marginRight: "0",
          }}
          disabled={true}
          value={email}
        />
        <div onClick={handleIsOpen}>
          <label
            className={style.label}
            htmlFor="vol-region"
          >{`활동지역`}</label>
          <div
            className={style.input_area}
            style={{ marginLeft: 0, marginRight: 0 }}
            id="vol-region"
          >
            {region ? region : "눌러서 거주지역을 선택하세요."}
          </div>
        </div>
        {isOpen && (
          <div className={style.modalbackground}>
            <RegionModal
              onClose={handleIsOpen}
              setSignupRegion={setRegion}
              setRegionCode={setRegionCode}
            />
          </div>
        )}
        <div className={style.permit_text}>
          <div>{`※ 위 정보들은 봉사활동 인증에 ※`}</div>
          <div>{`필요한 정보들입니다.`}</div>
        </div>

        <CommonButton
          text="다음으로"
          styles={{
            width: `${Math.min(
              500 - windowHeight * 0.05,
              windowWidth * 0.9,
            )}px`,
            backgroundColor: "#2CD261",
            position: "fixed",
            bottom: "56px",
            zIndex: `${isOpen ? -1 : 1000}`,
            boxSizing: "border-box",
            margin: `1.25rem 0`,
          }}
          onClick={() => {
            Swal.fire({
              icon: "question",
              text: "입력을 마치시겠습니까?",
              showCancelButton: true,
              confirmButtonText: "예",
              cancelButtonText: "아니오",
              confirmButtonColor: "#2CD261",
              cancelButtonColor: "#FF2953",
            }).then((result) => {
              if (result.isConfirmed) {
                dispatch(P.setIsEnd(true));
              }
            });
          }}
        />
      </div>
    </div>
  );
};

export default PloggingVolunteerInput;
