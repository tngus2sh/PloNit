import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { userActions } from "store/user-slice";
import Input from "components/common/Input";
import CommonButton from "components/common/CommonButton";
import RegionModal from "components/common/RegionModal";
import { nicknameCheck } from "api/lib/auth";
import { addInfo, getProfile } from "api/lib/members";
import style from "styles/css/AddInfoPage.module.css";
import DatePicker, { registerLocale } from "react-datepicker";
import ko from "date-fns/locale/ko";
import "custom_css/CrewCreateDatePicker.css";
import { Icon } from "@iconify/react";

const AddInfoPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const [isSignupName, setSignupName] = useState("");
  const [isSignupNickname, setSignupNickname] = useState("");
  const [isSignupGender, setSignupGender] = useState(false);
  const [isSignupBirth, setSignupBirth] = useState(null);
  const [isSignupRegion, setSignupRegion] = useState("");
  const [isRegionCode, setRegionCode] = useState(0);
  const [isnickname, setnickname] = useState(false);
  const [isOpenRegionModal, setOpenRegionModal] = useState(false);

  const onChangeName = (event: any) => {
    const newName = event.target.value;
    // 정규식을 사용하여 특수 기호 검사
    const specialCharacters = /[!@#$%^&*(),.?":{}|<>]/g;
    if (specialCharacters.test(newName)) {
      // 특수 기호가 포함되어 있으면 입력을 무시하고 기존의 값으로 설정
      return;
    }
    setSignupName(newName);
  };

  const onChangeNickname = (event: any) => {
    const newNickname = event.target.value;
    setSignupNickname(newNickname);
    nicknameCheck(
      newNickname,
      (res) => {
        console.log("닉네임 중복 확인");
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

  const onChangeBirthDate = (date: any) => {
    setSignupBirth(date);
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
      dongCode: isRegionCode,
      region: isSignupRegion,
    };
    if (!data.name) {
      alert("이름을 입력하세요");
      return;
    } else if (!data.nickname) {
      alert("닉네임을 입력하세요");
      return;
    } else if (!isnickname) {
      alert("중복된 닉네임입니다");
      return;
    } else if (!data.birth) {
      alert("생년월일을 입력하세요");
      return;
    } else if (!data.dongCode) {
      alert("활동 지역을 입력하세요");
      return;
    }
    console.log(data);

    addInfo(
      accessToken,
      data,
      (res) => {
        getProfile(
          accessToken,
          (res) => {
            console.log("내 정보 조회 성공");
            dispatch(userActions.saveMemberInfo(res.data.resultBody));
          },
          (err) => {
            console.log("내 정보 조회 실패", err);
          },
        );
        console.log("추가정보 입력 성공");
        navigate("/");
      },
      (err) => {
        console.log("추가정보 에러...", err);
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
      <div className={style.birth}>
        <label className={style.label} htmlFor="date_time">
          시간
        </label>
        <DatePicker
          selected={isSignupBirth}
          className={style.datepicker}
          name="date_time"
          id="date_time"
          placeholderText="시작 일시"
          onChange={onChangeBirthDate}
          dateFormat="yyyy.MM.dd"
          locale={ko}
          showPopperArrow={false}
          fixedHeight
          renderCustomHeader={({
            date,
            decreaseMonth,
            increaseMonth,
            prevMonthButtonDisabled,
            nextMonthButtonDisabled,
          }) => (
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                fontSize: "1.1rem",
              }}
              className="custom-react-datepicker__select-wrapper"
            >
              <button
                onClick={decreaseMonth}
                disabled={prevMonthButtonDisabled}
                className="custom_btn"
              >
                <Icon
                  icon="bi:chevron-left"
                  style={{ width: "1.5rem", height: "1.5rem" }}
                />
              </button>
              <div>
                {date.getFullYear()}년 {date.getMonth()}월
              </div>
              <button
                onClick={increaseMonth}
                disabled={nextMonthButtonDisabled}
                className="custom_btn"
              >
                <Icon
                  icon="bi:chevron-right"
                  style={{ width: "1.5rem", height: "1.5rem" }}
                />
              </button>
            </div>
          )}
        />
      </div>
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
              setRegionCode={setRegionCode}
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
    </div>
  );
};

export default AddInfoPage;
