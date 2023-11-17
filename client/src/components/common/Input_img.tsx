import { width } from "@mui/system";
import React from "react";
import style from "styles/css/Common/Input_img.module.css";

interface InputImgProps {
  labelTitle?: string;
  type: string;
  id?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

const Input_img: React.FC<InputImgProps> = ({
  type,
  onChange,
  labelTitle,
  id,
}) => {
  return (
    <div>
      {labelTitle && <label>{labelTitle}</label>}
      <div
        className={style.imageContainer}
        onClick={() => document.getElementById("hiddenFileInput")?.click()}
      >
        <img
          src="/plus.png"
          alt="upload-icon"
          style={{ width: "3rem", height: "3rem" }}
        />
      </div>
      <input
        id="hiddenFileInput"
        type={type}
        onChange={onChange}
        style={{ display: "none" }}
      />
    </div>
  );
};
export default Input_img;
