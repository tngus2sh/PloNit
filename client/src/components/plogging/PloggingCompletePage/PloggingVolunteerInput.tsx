import React, { useEffect, useRef } from "react";
import useCamera from "../functions/useCamera";

import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";
import * as camera from "store/camera-slice";

const PloggingVolunteerInput = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isVolEnd = useSelector<rootState, boolean>((state) => {
    return state.plogging.isVolEnd;
  });

  const { image, handleImageCapture, fileInputRef } = useCamera();
  const check = useRef<boolean>(false);

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
    <div>
      <input
        type="file"
        accept="image/*"
        capture="environment"
        id="cameraInput-IND"
        ref={fileInputRef}
        style={{ display: "none" }}
      />
    </div>
  );
};

export default PloggingVolunteerInput;
