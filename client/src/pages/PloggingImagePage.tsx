import React, { useEffect, useState } from "react";
import ImageCropper from "components/plogging/PloggingImagePage/ImageCropper";
import { useNavigate } from "react-router-dom";
import * as P from "store/plogging-slice";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import { setImage, setIsOnWrite } from "store/camera-slice";

import { savePloggingImage } from "api/lib/plogging";

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
  const beforeEnd = useSelector<rootState, boolean>((state) => {
    return state.plogging.beforeEnd;
  });
  const accessToken = useSelector<rootState, string>((state) => {
    return state.user.accessToken;
  });
  const ploggingId = useSelector<rootState, number>((state) => {
    return state.plogging.ploggingId;
  });

  useEffect(() => {
    async function saveImage() {
      if (uploadImage) {
        const blob = await fetch(uploadImage).then((response) =>
          response.blob(),
        );
        if (blob) {
          const jpgFile = new File([blob], "image.jpg", { type: "image/jpeg" });
          savePloggingImage({
            accessToken: accessToken,
            plogging_id: ploggingId,
            image: jpgFile,
            success: (response) => {
              console.log("성공");
              console.log(response);
              dispatch(P.addImage(response.data.resultBody));
              if (isEnd || beforeEnd) {
                navigate("/plogging/complete");
              } else {
                navigate("/plogging");
              }
            },
            fail: (error) => {
              console.error(error);
            },
          });
        }
      }
    }

    if (uploadImage) {
      if (isEnd || beforeEnd || target === "save") {
        saveImage();
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
    </div>
  );
};

export default PloggingImagePage;
