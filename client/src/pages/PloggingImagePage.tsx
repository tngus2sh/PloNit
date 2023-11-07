import React, { useEffect, useState } from "react";
import ImageCropper from "components/plogging/PloggingImagePage/ImageCropper";
import useImageCompress from "components/plogging/functions/useImageCompress";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";
import { setImage, setIsOnWrite } from "store/camera-slice";

import { savePloggingImage } from "api/lib/plogging";

const PloggingImagePage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
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

  const [uploadImage, setUploadImage] = useState<string | null>(null);
  function handleUploadImage(image: string) {
    setUploadImage(image);
  }
  const [compressedImage, setCompressedImage] = useState<File | null>(null);
  const { compressImage } = useImageCompress();

  useEffect(() => {
    async function handleCompressImage() {
      if (!uploadImage) return;

      if (target === "help") {
        dispatch(setImage(uploadImage));
        dispatch(setIsOnWrite(true));
        navigate("/plogging");
        return;
      }

      console.log("이미지 비동기 저장 로직 실행!");
      if (isEnd || beforeEnd) {
        navigate("/plogging/complete");
      } else {
        navigate("/plogging");
      }

      const blob = await fetch(uploadImage).then((response) => response.blob());
      if (blob) {
        const jpgFile = new File([blob], "iamge.jpg", { type: "image/jpeg" });
        console.log("before compress", jpgFile.size / (1024 * 1024));
        const compressedImage = await compressImage(jpgFile);

        if (compressedImage) {
          setCompressedImage(compressedImage);
          console.log("after compress", compressedImage.size / (1024 * 1024));

          savePloggingImage({
            accessToken: accessToken,
            plogging_id: ploggingId,
            image: compressedImage,
            success: (response) => {
              console.log("성공");
              console.log(response);
              dispatch(P.addImage(response.data.resultBody));
            },
            fail: (error) => {
              console.log(error);
            },
          });
        }
      }
    }

    if (uploadImage) {
      handleCompressImage();
    }
  }, [uploadImage]);

  // useEffect(() => {
  //   async function saveImage() {
  //     if (compressedImage) {
  //       savePloggingImage({
  //         accessToken: accessToken,
  //         plogging_id: ploggingId,
  //         image: compressedImage,
  //         success: (response) => {
  //           console.log("성공");
  //           console.log(response);
  //           dispatch(P.addImage(response.data.resultBody));
  //           if (isEnd || beforeEnd) {
  //             navigate("/plogging/complete");
  //           } else {
  //             navigate("/plogging");
  //           }
  //         },
  //         fail: (error) => {
  //           console.error(error);
  //         },
  //       });
  //     }
  //   }

  //   if (compressedImage) {
  //     if (isEnd || beforeEnd || target === "save") {
  //       saveImage();
  //     }
  //   }
  // }, [compressedImage]);

  return (
    <div style={{ height: windowHeight, width: "100%" }}>
      <ImageCropper aespectRatio={1 / 1} onCrop={handleUploadImage} />
    </div>
  );
};

export default PloggingImagePage;
