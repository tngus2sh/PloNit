import React, { useState } from "react";
import Input from "components/common/Input";
import { BackTopBar } from "components/common/TopBar";
import style from "styles/css/CrewpingCreatePage.module.css";
import CrewpingImg from "components/CrewpingCreate/CrewpingImg";
import CrewpingDate from "components/CrewpingCreate/CrewpingDate";
import CrewpingAddress from "components/CrewpingCreate/CrewpingAddress";
import CrewpingPeople from "components/CrewpingCreate/CrewpingPeople";
import CommonButton from "components/common/CommonButton";

const CrewpingCreatePage = () => {
  return (
    <div>
      <BackTopBar text="크루핑 생성" />
      <CrewpingImg />
      <Input id="crewping_name" labelTitle="크루핑 이름" type="text" />
      <CrewpingDate />
      <CrewpingAddress />
      <CrewpingPeople />
      <div className={style.introduce}>
        <label className={style.label} htmlFor="crewping_introduce">
          활동 소개
        </label>
        <textarea
          className={style.inputBox}
          name="crewping_introduce"
          id="crewping_introduce"
        ></textarea>
      </div>
      <CommonButton
        text="크루핑 생성"
        styles={{
          backgroundColor: "#2cd261",
        }}
      />
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewpingCreatePage;
