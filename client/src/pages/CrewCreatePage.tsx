import React, { useState, useRef } from "react";
import { useSelector, useDispatch } from "react-redux";
import Input from "components/common/Input";
import { BackTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
import Input_img from "components/common/Input_img";
import { getCrewCreate } from "api/lib/crew";
import style from "styles/css/CrewCreatePage.module.css";

interface SetFunction {
  setSelectedImage: React.Dispatch<React.SetStateAction<File>>;
}

const CrewCreatePage = () => {
  const imgRef = useRef();
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [crewInput, setCrewInput] = useState({
    name: "",
    crewImage: "",
    introduce: "",
    region: "",
  });
  const onChange = (event: any) => {
    const { id, value } = event.target;
    setCrewInput((prevState) => ({
      ...prevState,
      [id]: value,
    }));
  };
  const crewCreateHandler = () => {
    const data = crewInput;
    console.log(data);
    getCrewCreate(
      accessToken,
      data,
      (res) => {
        console.log("크루 생성 성공");
      },
      (err) => {
        console.log("크루 생성 에러");
      },
    );
  };
  const handleImageUpload = (event: any) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();

      reader.onload = (event: any) => {
        const dataURL = event.target.result;

        setCrewInput((prevCrewInput) => ({
          ...prevCrewInput,
          crewImage: dataURL,
        }));
      };

      reader.readAsDataURL(file);
    }
  };

  return (
    <div className={style.crew_create}>
      <BackTopBar text="크루 생성" />
      <img
        src={crewInput.crewImage ? crewInput.crewImage : `/metamong.png`}
        alt="프로필 이미지"
      />
      <Input_img
        type="file"
        labelTitle="이미지 업로드"
        id="crewImage"
        onChange={handleImageUpload}
      />
      <Input
        id="name"
        labelTitle="크루 이름"
        type="text"
        value={crewInput.name}
        onChange={onChange}
      />
      <Input
        id="introduce"
        labelTitle="크루 소개"
        type="text"
        value={crewInput.introduce}
        onChange={onChange}
      />
      <Input
        id="region"
        labelTitle="주요 활동 지역"
        type="text"
        value={crewInput.region}
        onChange={onChange}
      />

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
