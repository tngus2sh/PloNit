import React from "react";
import { BackTopBar } from "components/common/TopBar";
import Input from "components/common/Input";
import CommonButton from "components/common/CommonButton";
import { Icon } from "@iconify/react";
import style from "styles/css/ProfileEditPage.module.css";

const ProfileEditPage = () => {
  return (
    <div>
      <BackTopBar text="내 정보 수정" />
      <div className={style.profile_edit}>
        <div className={style.profile_img}>
          <img src="/metamong.png" alt="몽" />
          <div className={style.img_edit_icon}>
            <Icon
              icon="bi:pencil"
              className={style.icon}
              style={{ width: "1.5rem", height: "1.5rem" }}
            />
          </div>
        </div>
        <Input id="name" labelTitle="이름" type="text" maxLength={20} />
        <Input id="nickname" labelTitle="닉네임" type="text" maxLength={20} />
        <Input id="region" labelTitle="활동 지역" type="text" />
        <Input id="weight" labelTitle="체중" type="number" />
        <Input id="id1365" labelTitle="1365ID" type="text" />
        <CommonButton
          text="수정완료"
          styles={{
            backgroundColor: "#2cd261",
          }}
        />
      </div>
    </div>
  );
};

export default ProfileEditPage;
