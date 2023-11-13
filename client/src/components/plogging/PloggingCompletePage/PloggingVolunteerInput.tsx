import React, { useState, useEffect, useRef } from "react";
import useCamera from "../functions/useCamera";
import CommonButton from "components/common/CommonButton";
import Input from "components/common/Input";
import { BasicTopBar } from "components/common/TopBar";
import Swal from "sweetalert2";
import { renderToString } from "react-dom/server";
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
    return state.user.info.name;
  });
  const reduxEmail = useSelector<rootState, string>((state) => {
    return state.user.info.email;
  });
  const reduxId1365 = useSelector<rootState, string>((state) => {
    return state.user.info.id1365;
  });
  const isVolEnd = useSelector<rootState, boolean>((state) => {
    return state.plogging.isVolEnd;
  });
  const accessToken = useSelector<rootState, string>((state) => {
    return state.user.auth.accessToken;
  });
  const ploggingId = useSelector<rootState, number>((state) => {
    return state.plogging.ploggingId;
  });
  const birth = useSelector<rootState, string>((state) => {
    return state.user.info.birthday;
  });

  const { image, handleImageCapture, fileInputRef } = useCamera();
  const check = useRef<boolean>(false);
  const [name, setName] = useState<string>(reduxName);
  const [phoneNumber, setPhoneNumber] = useState<string>("");
  const [id_1365, setId_1365] = useState<string>(reduxId1365);
  const [email, setEmail] = useState<string>(reduxEmail);

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

  useEffect(() => {
    if (phoneNumber.length === 10) {
      setPhoneNumber((current) => {
        return current
          .replace(/-/g, "")
          .replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
      });
    } else if (phoneNumber.length === 13) {
      setPhoneNumber((current) => {
        {
          return current
            .replace(/-/g, "")
            .replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
        }
      });
    }
  }, [phoneNumber]);

  function onClick() {
    if (!name || phoneNumber.length != 13 || !id_1365) {
      Swal.fire({
        icon: "warning",
        title: "입력 정보 불충분",
        html: renderToString(
          <div>
            <div>{`입력 정보가 충분하지 않습니다.`}</div>
            <div>{`그래도 종료하시겠습니까?`}</div>
          </div>,
        ),
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
    } else {
      Swal.fire({
        icon: "question",
        html: renderToString(
          <div>
            <div>{`완전한 정보가 아니면`}</div>
            <div>{`봉사가 등록되지 않습니다.`}</div>
            <br />
            <div>{`입력을 마치시겠습니까?`}</div>
          </div>,
        ),
        showCancelButton: true,
        confirmButtonText: "예",
        cancelButtonText: "아니오",
        confirmButtonColor: "#2CD261",
        cancelButtonColor: "#FF2953",
      }).then((result) => {
        if (result.isConfirmed) {
          const formedPhoneNumber = phoneNumber.replace(/-/g, "");
          registerVolInfo({
            accessToken,
            ploggingId,
            name,
            phoneNumber: formedPhoneNumber,
            id1365: id_1365,
            email: email,
            birth,
            success: (response) => {
              console.log("봉사 저장 성공!");
              console.log(response);
              dispatch(P.setIsEnd(true));
            },
            fail: (error) => {
              console.error(error);
            },
          });
        }
      });
    }
  }

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
          labelTitle="전화번호"
          id="vol-phoneNumber"
          styles={{
            marginLeft: "0",
            marginRight: "0",
          }}
          value={phoneNumber}
          onChange={(event: React.ChangeEvent<HTMLInputElement>) => {
            const regex = /^[0-9\b -]{0,13}$/;
            const { value } = event.target;
            if (value.length <= 13 && regex.test(value)) {
              setPhoneNumber(value);
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
            zIndex: `1000`,
            boxSizing: "border-box",
            margin: `1.25rem 0`,
          }}
          onClick={onClick}
        />
      </div>
    </div>
  );
};

export default PloggingVolunteerInput;
