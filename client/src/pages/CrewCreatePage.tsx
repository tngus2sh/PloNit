import React from "react";
import Input from "components/common/Input";
import { BackTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
import style from "styles/css/CrewpingCreatePage.module.css";
import CrewpingImg from "components/CrewpingCreate/CrewpingImg";
import CrewpingDate from "components/CrewpingCreate/CrewpingDate";
import CrewpingAddress from "components/CrewpingCreate/CrewpingAddress";
import CrewpingPeople from "components/CrewpingCreate/CrewpingPeople";
import Input_img from "components/common/Input_img";

const CrewCreatePage = () => {
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
      <Input id="crewp_name" labelTitle="크루 명" type="text" />
      <Input id="crewp_intro" labelTitle="크루 소개" type="text" />
      <Input id="crewp_location" labelTitle="주요 활동 지역" type="text" />
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
      />
    </div>
  );
};

export default CrewCreatePage;
