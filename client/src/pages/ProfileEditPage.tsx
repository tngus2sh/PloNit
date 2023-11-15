import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { userActions } from "store/user-slice";
import { BackTopBar } from "components/common/TopBar";
import Input from "components/common/Input";
import CommonButton from "components/common/CommonButton";
import RegionModal from "components/common/RegionModal";
import { Icon } from "@iconify/react";
import style from "styles/css/ProfileEditPage.module.css";
import { nicknameCheck } from "api/lib/auth";
import { getProfile, EditProfile } from "api/lib/members";
import { NotOkModal, OkModal } from "components/common/AlertModals";

const formattedDate = (date: any) => {
  const birthday = new Date(date);
  const year = birthday.getFullYear();
  const month = ("0" + (birthday.getMonth() + 1)).slice(-2);
  const day = ("0" + birthday.getDate()).slice(-2);
  return `${year}.${month}.${day}`;
};

const ProfileEditPage = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const User = useSelector((state: any) => state.user.info);

  const [isProfileImage, setProfileImage] = useState<Blob | string>("");
  console.log(isProfileImage);
  const [isNickname, setNickname] = useState(User.nickname);
  const [isAllowNickname, setAllowNickname] = useState(false);
  const [isRegion, setRegion] = useState(User.region);
  const [isRegionCode, setRegionCode] = useState(User.dongCode);
  const [isOpenRegionModal, setOpenRegionModal] = useState(false);
  const [isWeight, setWeight] = useState(User.weight);
  const [isId_1365, setId_1365] = useState(User.id1365);

  const handleImageUpload = (event: any) => {
    const file = event.target.files[0];
    if (file) {
      setProfileImage(file);
      console.log(isProfileImage);
    }
  };
  console.log(isProfileImage);
  const onChangeNickname = (event: any) => {
    const newNickname = event.target.value;
    setNickname(newNickname);
    nicknameCheck(
      newNickname,
      (res) => {
        console.log("닉네임 중복 확인 (수정)");
        console.log(res.data);
        setAllowNickname(res.data.resultBody.avl);
      },
      (err) => {
        console.log("닉네임 중복 확인 (수정) 에러...");
      },
    );
  };

  const OpenRegionModal = () => {
    setOpenRegionModal(!isOpenRegionModal);
  };

  const onChangeWeight = (event: any) => {
    setWeight(event.target.value);
  };

  const onChangeId_1365 = (event: any) => {
    setId_1365(event.target.value);
  };

  const SendEdit = () => {
    const formData = new FormData();
    formData.append("dongCode", isRegionCode);
    formData.append("nickname", isNickname);
    formData.append("weight", isWeight);
    formData.append("region", isRegion);
    formData.append("id1365", isId_1365);
    if (isProfileImage === "") {
      formData.append("profileImage", User.profileImage);
    } else {
      formData.append("profileImage", isProfileImage);
    }
    // formData.append("profileImage", isProfileImage);
    formData.append("birth", User.birth);
    formData.append("gender", User.gender);
    formData.append("name", User.name);

    EditProfile(
      accessToken,
      formData,
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
        console.log("회원 정보 수정 성공");
        OkModal({ text: "회원 정보 수정을 성공했습니다." });
        navigate("/profile");
      },
      (err) => {
        console.log("회원 정보 수정 실패", err);
        NotOkModal({ text: "회원 정보 수정을 실패했습니다." });
      },
    );
  };

  return (
    <div>
      <BackTopBar text="내 정보 수정" />
      <div className={style.profile_edit}>
        <div className={style.img_text}>
          <div className={style.profile_img}>
            <img
              src={
                isProfileImage instanceof Blob
                  ? URL.createObjectURL(isProfileImage)
                  : (isProfileImage as string)
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
          <div className={style.non_edit}>
            <div className={style.top_section}>
              <div className={style.name}>{User.name}</div>
              {User.gender ? (
                <div className={style.gender_woman}>여</div>
              ) : (
                <div className={style.gender_man}>남</div>
              )}
            </div>
            <div className={style.birth}>{formattedDate(User.birth)}</div>
          </div>
        </div>
        <Input
          id="nickname"
          labelTitle="닉네임"
          type="text"
          value={isNickname}
          onChange={onChangeNickname}
          placeholder="20자 이내로 입력해주세요"
          maxLength={20}
        />
        {isNickname !== User.nickname ? (
          isNickname ? (
            isAllowNickname ? (
              <div className={style.nickname_true}>
                사용가능한 닉네임입니다.
              </div>
            ) : (
              <div className={style.nickname_false}>중복된 닉네임입니다.</div>
            )
          ) : null
        ) : null}
        <div className={style.region}>
          <div className={style.title}>활동지역</div>
          <div className={style.input_area} onClick={OpenRegionModal}>
            {isRegion}
          </div>
          {isOpenRegionModal && (
            <>
              <div className={style.modalbackground}></div>
              <RegionModal
                onClose={OpenRegionModal}
                setSignupRegion={setRegion}
                setRegionCode={setRegionCode}
              />
            </>
          )}
        </div>
        <Input
          id="weight"
          labelTitle="체중(kg)"
          type="number"
          value={String(isWeight)}
          onChange={onChangeWeight}
        />
        <Input
          id="id1365"
          labelTitle="1365ID"
          type="text"
          value={isId_1365}
          onChange={onChangeId_1365}
        />
        <CommonButton
          text="수정완료"
          styles={{
            backgroundColor: "#2cd261",
          }}
          onClick={SendEdit}
        />
        <div style={{ height: "4rem" }}></div>
      </div>
    </div>
  );
};

export default ProfileEditPage;
