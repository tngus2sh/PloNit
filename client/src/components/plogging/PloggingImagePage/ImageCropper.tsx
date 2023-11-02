import React, { useEffect, useRef } from "react";
import { Cropper, ReactCropperElement } from "react-cropper";
import useCamera from "../functions/useCamera";
import CommonButton from "components/common/CommonButton";
import { BasicTopBar } from "components/common/TopBar";
import Swal from "sweetalert2";
import "cropperjs/dist/cropper.css";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import { setImage as setReduxImage } from "store/camera-slice";

interface PropsType {
  onCrop: (image: string) => void;
  aespectRatio: number;
}

const ImageCropper: React.FC<PropsType> = ({ onCrop, aespectRatio }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { image, setImage } = useCamera();
  const cropperRef = useRef<ReactCropperElement>(null);
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const cbURL = useSelector<rootState, string>((state) => {
    return state.plogging.cbURL;
  });
  const storedImage = useSelector<rootState, string>((state) => {
    return state.camera.value;
  });

  const trimmedHeight = windowHeight * 0.93 - 56;
  const bottomBtnPercent = 12;

  useEffect(() => {
    if (storedImage) {
      setImage(storedImage);
      dispatch(setReduxImage(""));
    } else {
      navigate(cbURL);
    }
  }, []);

  function rejectCrop() {
    Swal.fire({
      icon: "question",
      text: "이미지 업로드를 종료하시겠습니까?",
      showCancelButton: true,
      confirmButtonText: "예",
      cancelButtonText: "아니오",
      confirmButtonColor: "#2CD261",
      cancelButtonColor: "#FF2953",
    }).then((result) => {
      if (result.isConfirmed) {
        setImage(null);
        navigate(cbURL);
      }
    });
  }

  function getCropData() {
    function innerFn() {
      if (typeof cropperRef.current?.cropper !== "undefined") {
        const canvas = cropperRef.current?.cropper.getCroppedCanvas();
        const currentDate = new Date();
        const krTimeOptions: Intl.DateTimeFormatOptions = {
          timeZone: "Asia/Seoul",
          year: "numeric",
          month: "numeric",
          day: "numeric",
          hour: "numeric",
          minute: "numeric",
        };
        const krDate = currentDate.toLocaleDateString("ko-KR", krTimeOptions);
        const ctx = canvas.getContext("2d");
        if (ctx) {
          const { height } = canvas;
          const textSize = height / 20;
          const textWidth = ctx.measureText(krDate).width;
          ctx.font = `${textSize}px Arial`;
          ctx.fillStyle = `orange`;
          const x = (height - textWidth) / 2;
          const y = height * 0.9;
          ctx.fillText(krDate, x, y);
        }

        onCrop(canvas.toDataURL());
        setImage(null);
      }
    }

    Swal.fire({
      icon: "question",
      text: "이미지 크기 설정을 마치시겠습니까?",
      showCancelButton: true,
      confirmButtonText: "예",
      cancelButtonText: "아니오",
      confirmButtonColor: "#2CD261",
      cancelButtonColor: "#FF2953",
    }).then((result) => {
      if (result.isConfirmed) {
        innerFn();
      }
    });
  }

  return (
    <div style={{ height: "100%", width: "100%" }}>
      {image && (
        <div>
          <BasicTopBar text="이미지 크기 설정" />
          <div
            style={{
              height: `${trimmedHeight}px`,
              width: "100%",
              boxSizing: "border-box",
            }}
          >
            <Cropper
              style={{
                position: "relative",
                height: `${100 - bottomBtnPercent}%`,
                width: `100%`,
                boxSizing: "border-box",
                overflow: "hidden",
              }}
              ref={cropperRef}
              aspectRatio={aespectRatio}
              src={image}
              viewMode={1}
              background={false}
              responsive
              autoCropArea={1}
              checkOrientation={false}
              guides
              center
              dragMode="move"
            />
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                height: `${bottomBtnPercent}%`,
                width: "100%",
              }}
            >
              <div style={{ width: "50%" }}>
                <CommonButton
                  text="취소"
                  styles={{ backgroundColor: "#FF2953" }}
                  onClick={rejectCrop}
                />
              </div>
              <div style={{ width: "50%" }}>
                <CommonButton
                  text="완료"
                  styles={{ backgroundColor: "#2CD261" }}
                  onClick={getCropData}
                />
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ImageCropper;
