import React, { useEffect, useState } from "react";
import ImageCropper from "components/plogging/PloggingImagePage/ImageCropper";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

const PloggingImagePage = () => {
  const [uploadImage, setUploadImage] = useState<string | null>(null);
  function handleUploadImage(image: string) {
    setUploadImage(image);
  }
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.windowHeight.value;
  });

  return (
    <div style={{ height: windowHeight, width: "100%" }}>
      <ImageCropper aespectRatio={1 / 1} onCrop={handleUploadImage} />
      {/* {uploadImage && <img src={uploadImage} />} */}
    </div>
  );
};

export default PloggingImagePage;
