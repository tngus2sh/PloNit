import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import Input from "components/common/Input";
import { BackTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
import Input_img from "components/common/Input_img";
import { getCrewCreate } from "api/lib/crew";

const CrewCreatePage = () => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [crewInput, setCrewInput] = useState({
    name: "",
    crewImage: "",
    introduce: "",
    region: "",
  });
  const onChange = (event: any) => {
    // console.log("하하");
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
  const handleImageUpload = (e: React.ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files?.[0];
    if (file) {
      console.log("File chosen", file);
      // 이곳에서 파일을 처리하는 로직을 추가할 수 있습니다.
    }
  };

  return (
    <div>
      <BackTopBar text="크루 생성" />
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
      <Input_img
        type="file"
        labelTitle="이미지 업로드"
        onChange={handleImageUpload}
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
