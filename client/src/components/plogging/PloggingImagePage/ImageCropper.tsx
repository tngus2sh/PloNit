import React, { useEffect, useRef } from "react";
import { Cropper, ReactCropperElement } from "react-cropper";
import useCamera from "../functions/useCamera";
import CommonButton from "components/common/CommonButton";
import { BasicTopBar } from "components/common/TopBar";
import Swal from "sweetalert2";
import "cropperjs/dist/cropper.css";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

interface PropsType {
  onCrop: (image: string) => void;
  aespectRatio: number;
}

const ImageCropper: React.FC<PropsType> = ({ onCrop, aespectRatio }) => {
  const { image, setImage, handleImageCapture, fileInputRef } = useCamera();
  const preventDup = useRef<boolean>(true);
  const cropperRef = useRef<ReactCropperElement>(null);
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.windowHeight.value;
  });
  const BtnHeight = windowHeight * 0.12;
  const trimmedHeight = windowHeight * 0.93 - 56;

  useEffect(() => {
    if (preventDup.current) {
      handleImageCapture();
    }

    return () => {
      preventDup.current = false;
    };
  }, []);

  function getCropData() {
    if (typeof cropperRef.current?.cropper !== "undefined") {
      onCrop(cropperRef.current?.cropper.getCroppedCanvas().toDataURL());
      setImage(null);
    }
  }

  return (
    <div style={{ height: "100%", width: "100%" }}>
      <input
        type="file"
        accept="image/*"
        capture="environment"
        id="cameraInput"
        ref={fileInputRef}
        style={{ display: "none" }}
      />
      {image && (
        <div style={{ height: "100%", width: "100%", boxSizing: "border-box" }}>
          <BasicTopBar text="이미지 크기 설정" />
          <div>
            <Cropper
              ref={cropperRef}
              aspectRatio={aespectRatio}
              src={image}
              viewMode={1}
              height={trimmedHeight - BtnHeight}
              width={"90%"}
              background={false}
              responsive
              autoCropArea={1}
              checkOrientation={false}
              guides
              center
              dragMode="move"
            />
          </div>

          <div
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <div style={{ width: "50%" }}>
              <CommonButton
                text="취소"
                styles={{ backgroundColor: "#FF2953" }}
                onClick={() => {
                  setImage(null);
                }}
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
      )}
    </div>
  );
};

export default ImageCropper;
