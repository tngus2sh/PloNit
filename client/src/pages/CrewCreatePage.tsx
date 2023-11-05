import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import Input from "components/common/Input";
import { BackTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
import { getCrewCreate } from "api/lib/crew";
import { Icon } from "@iconify/react";
import style from "styles/css/CrewCreatePage.module.css";

const CrewCreatePage = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [isCrewName, setCrewName] = useState("");
  const [isCrewRegion, setCrewRegion] = useState("");
  const [isCrewIntroduce, setCrewIntroduce] = useState("");
  const [isCrewImage, setCrewImage] = useState(null);

  const onChangeName = (event: any) => {
    setCrewName(event.target.value);
  };
  const onChangeRegion = (event: any) => {
    setCrewRegion(event.target.value);
  };
  const onChangeIntroduce = (event: any) => {
    setCrewIntroduce(event.target.value);
  };

  const crewCreateHandler = () => {
    if (!isCrewName) {
      alert("크루 이름을 입력하세요");
    } else if (!isCrewIntroduce) {
      alert("크루 소개를 입력하세요");
    }
    const formData = new FormData();
    formData.append("name", isCrewName);
    formData.append("region", isCrewRegion);
    formData.append("introduce", isCrewIntroduce);
    if (isCrewImage) {
      formData.append("crewImage", isCrewImage);
    }
    console.log(formData);
    getCrewCreate(
      accessToken,
      formData,
      (res) => {
        console.log(res.data);
        console.log("크루 생성 성공");
        navigate("/crew/list");
      },
      (err) => {
        console.log("크루 생성 에러", err);
      },
    );
  };
  const handleImageUpload = (event: any) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = (event: any) => {
        const dataURL = event.target.result;
        setCrewImage(file);
      };

      reader.readAsDataURL(file);
    }
  };

  return (
    <div className={style.crew_create}>
      <BackTopBar text="크루 생성" />
      <div className={style.crew_img}>
        <img
          src={isCrewImage ? URL.createObjectURL(isCrewImage) : `/metamong.png`}
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
      <Input
        id="name"
        labelTitle="크루 이름"
        type="text"
        value={isCrewName}
        onChange={onChangeName}
      />
      <Input
        id="region"
        labelTitle="주요 활동 지역"
        type="text"
        value={isCrewRegion}
        onChange={onChangeRegion}
      />
      <div className={style.introduce}>
        <label className={style.label} htmlFor="introduce">
          크루 소개
        </label>
        <textarea
          className={style.inputBox}
          name="introduce"
          id="introduce"
          value={isCrewIntroduce}
          onChange={onChangeIntroduce}
        ></textarea>
      </div>
      <CommonButton
        text="크루 생성"
        styles={{
          backgroundColor: "#2cd261",
        }}
        onClick={crewCreateHandler}
      />
    </div>
  );
};

export default CrewCreatePage;
