import React, { useEffect, useState } from "react";
import ImageCropper from "components/plogging/PloggingImagePage/ImageCropper";
import { useNavigate } from "react-router-dom";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import { setImage, setIsOnWrite } from "store/camera-slice";
import * as P from "store/plogging-slice";

const PloggingImagePage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const [uploadImage, setUploadImage] = useState<string | null>(null);
  function handleUploadImage(image: string) {
    setUploadImage(image);
  }
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const target = useSelector<rootState, string>((state) => {
    return state.camera.target;
  });
  const isEnd = useSelector<rootState, boolean>((state) => {
    return state.plogging.isEnd;
  });

  useEffect(() => {
    async function saveImage() {
      if (uploadImage) {
        const blob = await fetch(uploadImage).then((response) =>
          response.blob(),
        );
        if (blob) {
          const jpgFile = new File([blob], "image.jpg", { type: "image/jpeg" });
          console.log(jpgFile); // 추후 제거
          // axios로 전달하는 것 필요함.
          // 그리고 images에 저장할 할 url을 받아와서 저장해야 함.
        }
      }
    }

    if (uploadImage) {
      if (isEnd) {
        saveImage();
        dispatch(P.addImage("/metamong.png")); // 추후 제거(더미 테스트용)
        navigate("/plogging/complete");
        return;
      }
      if (target === "save") {
        saveImage();
        navigate("/plogging");
      } else {
        dispatch(setImage(uploadImage));
        dispatch(setIsOnWrite(true));
        navigate("/plogging");
      }
    }
  }, [uploadImage]);

  return (
    <div style={{ height: windowHeight, width: "100%" }}>
      <ImageCropper aespectRatio={1 / 1} onCrop={handleUploadImage} />
      {/* {uploadImage && <img src={uploadImage} />} */}
    </div>
  );
};

export default PloggingImagePage;
