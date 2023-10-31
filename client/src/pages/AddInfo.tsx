import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Input from "components/common/Input";
import CommonButton from "components/common/CommonButton";
import { addInfo } from "api/lib/members";
import { useSelector } from "react-redux";

const AddInfo = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [signupInput, setSignupInput] = useState({
    name: "",
    nickname: "",
    gender: "",
    birth: "",
    region: "",
  });
  const onChange = (event: any) => {
    const { id, value } = event.target;
    setSignupInput((prevState) => ({
      ...prevState,
      [id]: value,
    }));
  };
  const onSubmit = (event: any) => {
    event.preventDefault();
    const data = signupInput;
    addInfo(
      accessToken,
      data,
      (res) => {
        console.log("추가정보 입력 성공");
        navigate("/");
      },
      (err) => {
        console.log("추가정보 에러...");
      },
    );
  };

  return (
    <div>
      <div> 서비스 사용을 위해 추가 정보를 등록해 주세요!</div>
      <form onSubmit={onSubmit}>
        <Input
          id="name"
          labelTitle="이름"
          type="text"
          value={signupInput.name}
          onChange={onChange}
        />
        <Input
          id="nickname"
          labelTitle="닉네임"
          type="text"
          value={signupInput.nickname}
          onChange={onChange}
        />
        <Input
          id="gender"
          labelTitle="성별"
          type="text"
          value={signupInput.gender}
          onChange={onChange}
        />
        <Input
          id="birth"
          labelTitle="생년월일"
          type="text"
          value={signupInput.birth}
          onChange={onChange}
        />
        <Input
          id="region"
          labelTitle="활동 지역"
          type="text"
          value={signupInput.region}
          onChange={onChange}
        />
        <CommonButton
          text="시작하기"
          styles={{
            backgroundColor: "#2cd261",
          }}
        />
      </form>
    </div>
  );
};

export default AddInfo;
