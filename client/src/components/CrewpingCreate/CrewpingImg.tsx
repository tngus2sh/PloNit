import React, { useState } from "react";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewpingCreatePage.module.css";

const CrewpingImg = ({ setCrewpingImage, isCrewpingImage }: any) => {
  const handleImageUpload = (event: any) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = (event: any) => {
        const dataURL = event.target.result;
        setCrewpingImage(file);
      };

      reader.readAsDataURL(file);
    }
  };

  return (
    <div className={style.crewping_img}>
      <img
        src={
          isCrewpingImage
            ? URL.createObjectURL(isCrewpingImage)
            : `/NoImage.png`
        }
        alt="프로필 이미지"
      />
      <label className={style.img_edit_icon} htmlFor="input_file">
        <Icon
          icon="bi:pencil"
          className={style.icon}
          style={{ width: "1.5rem", height: "1.5rem" }}
        />
      </label>
      <input
        type="file"
        id="input_file"
        accept="image/*"
        style={{ display: "none" }}
        onChange={handleImageUpload}
      />
    </div>
  );
};

export default CrewpingImg;
