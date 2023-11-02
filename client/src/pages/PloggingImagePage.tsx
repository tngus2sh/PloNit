import React, { useEffect, useState } from "react";
import ImageCropper from "components/plogging/PloggingImagePage/ImageCropper";
import { useNavigate } from "react-router-dom";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import { setImage, setIsOnWrite } from "store/camera-slice";

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

  const cbURL = useSelector<rootState, string>((state) => {
    return state.plogging.cbURL;
  });
  const target = useSelector<rootState, string>((state) => {
    return state.camera.target;
  });

  useEffect(() => {
    async function saveImage() {
      if (uploadImage) {
        const blob = await fetch(uploadImage).then((response) =>
          response.blob(),
        );
        if (blob) {
          const jpgFile = new File([blob], "image.jpg", { type: "image/jpeg" });
          // console.log(jpgFile);
        }
      }
    }
    if (uploadImage) {
      if (target === "save") {
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
      {/* {uploadImage && <img src={uploadImage} />} */}
    </div>
  );
};

export default PloggingImagePage;
