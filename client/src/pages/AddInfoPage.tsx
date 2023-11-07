import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import Input from "components/common/Input";
import CommonButton from "components/common/CommonButton";
import RegionModal from "components/common/RegionModal";
import { nicknameCheck } from "api/lib/auth";
import { addInfo } from "api/lib/members";
import style from "styles/css/AddInfoPage.module.css";

const AddInfoPage = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [isSignupName, setSignupName] = useState("");
  const [isSignupNickname, setSignupNickname] = useState("");
  const [isSignupGender, setSignupGender] = useState(false);
  const [isSignupBirth, setSignupBirth] = useState("");
  const [isSignupRegion, setSignupRegion] = useState("");
  const [isnickname, setnickname] = useState(false);
  const [isOpenRegionModal, setOpenRegionModal] = useState(false);

  const onChangeName = (event: any) => {
    setSignupName(event.target.value);
  };

  const onChangeNickname = (event: any) => {
    const newNickname = event.target.value;
    setSignupNickname(newNickname);
    nicknameCheck(
      newNickname,
      (res) => {
        console.log("닉네임 중복 확인");
        console.log(res);
        console.log(res.data);
        setnickname(res.data.resultBody.avl);
      },
      (err) => {
        console.log("닉네임 중복 확인 에러...");
      },
    );
  };

  const onChangeGender = (value: boolean) => {
    setSignupGender(value);
  };

  const onChangeBirth = (event: any) => {
    setSignupBirth(event.target.value);
  };

  const OpenRegionModal = () => {
    setOpenRegionModal(!isOpenRegionModal);
  };

  const SendInfo = () => {
    const data = {
      name: isSignupName,
      nickname: isSignupNickname,
      gender: isSignupGender,
      birth: isSignupBirth,
      region: isSignupRegion,
    };
    if (!data.name) {
      alert("이름을 입력하세요");
      return;
    } else if (!data.nickname) {
      alert("닉네임을 입력하세요");
    } else if (!isnickname) {
      alert("중복된 닉네임입니다");
    } else if (!data.birth) {
      alert("생년월일을 입력하세요");
    } else if (data.birth.length > 6) {
      alert("생년월일을 다시 입력하세요");
    }
    // else if (!data.region) {
    //   alert("활동 지역을 입력하세요");
    // }
    console.log(data);
    console.log(accessToken);
    addInfo(
      accessToken,
      data,
      (res) => {
        console.log("추가정보 입력 성공");
        navigate("/");
      },
      (err) => {
        console.log(accessToken, "토큰");
        console.log("추가정보 에러...");
      },
    );
  };

  return (
    <div className={style.add_info}>
      <img src="/plonit_logo.png" alt="로고" />
      <div className={style.guide}>
        <div>서비스 사용을 위해</div>
        <div>추가 정보를 등록해 주세요!</div>
      </div>
      <Input
        id="name"
        labelTitle="이름"
        type="text"
        value={isSignupName}
        onChange={onChangeName}
        placeholder="20자 이내로 입력해주세요"
        maxLength={20}
      />
      <Input
        id="nickname"
        labelTitle="닉네임"
        type="text"
        value={isSignupNickname}
        onChange={onChangeNickname}
        placeholder="20자 이내로 입력해주세요"
        maxLength={20}
      />
      {isSignupNickname ? (
        isnickname ? (
          <div className={style.nickname_true}>사용가능한 닉네임입니다.</div>
        ) : (
          <div className={style.nickname_false}>중복된 닉네임입니다.</div>
        )
      ) : null}

      <div className={style.gender}>
        <div
          className={
            isSignupGender === false
              ? `${style.choice1} ${style.selected}`
              : style.choice1
          }
          onClick={() => onChangeGender(false)}
        >
          남
        </div>
        <div
          className={
            isSignupGender === true
              ? `${style.choice2} ${style.selected}`
              : style.choice2
          }
          onClick={() => onChangeGender(true)}
        >
          여
        </div>
      </div>
      <Input
        id="birth"
        labelTitle="생년월일"
        type="number"
        value={isSignupBirth}
        placeholder="900101"
        onChange={onChangeBirth}
        maxLength={6}
      />
      <div className={style.region}>
        <div className={style.title}>활동지역</div>
        <div className={style.input_area} onClick={OpenRegionModal}>
          {isSignupRegion}
        </div>
        {isOpenRegionModal && (
          <>
            <div className={style.modalbackground}></div>
            <RegionModal
              onClose={OpenRegionModal}
              setSignupRegion={setSignupRegion}
            />
          </>
        )}
      </div>
      <CommonButton
        text="시작하기"
        styles={{
          backgroundColor: "#2cd261",
        }}
        onClick={SendInfo}
      />

      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default AddInfoPage;
