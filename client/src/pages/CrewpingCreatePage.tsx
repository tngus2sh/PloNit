import React, { useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import Input from "components/common/Input";
import { BackTopBar } from "components/common/TopBar";
import style from "styles/css/CrewpingCreatePage.module.css";
import CrewpingImg from "components/CrewpingCreate/CrewpingImg";
import CrewpingDate from "components/CrewpingCreate/CrewpingDate";
import CrewpingAddress from "components/CrewpingCreate/CrewpingAddress";
import CrewpingPeople from "components/CrewpingCreate/CrewpingPeople";
import CommonButton from "components/common/CommonButton";
import { getCrewpingCreate } from "api/lib/crewping";

const CrewpingCreatePage = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const { crewId } = useParams();
  const [isCrewpingName, setCrewpingName] = useState("");
  const [isCrewpingPlace, setCrewpingPlace] = useState("");
  const [isCrewpingIntroduce, setCrewpingIntroduce] = useState("");
  const [isCrewpingImage, setCrewpingImage] = useState(null);
  const [isCrewpingStartDate, setCrewpingStartDate] = useState("");
  const [isCrewpingEndDate, setCrewpingEndDate] = useState("");
  const [isCrewpingMaxPeople, setCrewpingMaxPeople] = useState<number>(0);

  console.log(isCrewpingStartDate, isCrewpingEndDate);
  const onChangeName = (event: any) => {
    setCrewpingName(event.target.value);
  };
  const onChangeIntroduce = (event: any) => {
    setCrewpingIntroduce(event.target.value);
  };

  const crewpingCreateHandler = () => {
    const formData = new FormData();
    if (crewId) {
      formData.append("crewId", crewId);
    }
    formData.append("name", isCrewpingName);
    if (isCrewpingImage) {
      formData.append("crewpingImage", isCrewpingImage);
    }
    formData.append("startDate", isCrewpingStartDate);
    formData.append("endDate", isCrewpingEndDate);
    formData.append("maxPeople", isCrewpingMaxPeople.toString());
    formData.append("place", isCrewpingPlace);
    formData.append("introduce", isCrewpingIntroduce);

    console.log(formData);
    if (!isCrewpingName) {
      alert("크루핑 이름을 입력하세요");
      return;
    } else if (!isCrewpingImage) {
      alert("크루핑 이미지를 입력하세요");
      return;
    } else if (!isCrewpingStartDate) {
      alert("크루핑 시작시간을 입력하세요");
      return;
    } else if (!isCrewpingEndDate) {
      alert("크루핑 종료시간을 입력하세요");
      return;
    } else if (!isCrewpingMaxPeople) {
      alert("크루핑 참여인원을 입력하세요");
      return;
    } else if (!isCrewpingPlace) {
      alert("크루핑 장소를 입력하세요");
      return;
    } else if (!isCrewpingIntroduce) {
      alert("크루핑 소개를 입력하세요");
      return;
    }

    getCrewpingCreate(
      accessToken,
      formData,
      (res) => {
        console.log(res.data);
        console.log("크루핑 생성 성공");
        navigate(`/crew/community/${crewId}`);
      },
      (err) => {
        console.log("크루핑 생성 에러", err);
      },
    );
  };

  return (
    <div>
      <BackTopBar text="크루핑 생성" />
      <CrewpingImg
        setCrewpingImage={setCrewpingImage}
        isCrewpingImage={isCrewpingImage}
      />
      <Input
        id="crewping_name"
        labelTitle="크루핑 이름"
        type="text"
        value={isCrewpingName}
        onChange={onChangeName}
      />
      <CrewpingDate
        setCrewpingStartDate={setCrewpingStartDate}
        setCrewpingEndDate={setCrewpingEndDate}
      />
      <CrewpingAddress setCrewpingPlace={setCrewpingPlace} />
      <CrewpingPeople setCrewpingMaxPeople={setCrewpingMaxPeople} />
      <div className={style.introduce}>
        <label className={style.label} htmlFor="crewping_introduce">
          활동 소개
        </label>
        <textarea
          className={style.inputBox}
          name="crewping_introduce"
          id="crewping_introduce"
          value={isCrewpingIntroduce}
          onChange={onChangeIntroduce}
        ></textarea>
      </div>
      <CommonButton
        text="크루핑 생성"
        styles={{
          backgroundColor: "#2cd261",
        }}
        onClick={crewpingCreateHandler}
      />
      <div style={{ height: "4rem" }}></div>
    </div>
  );
};

export default CrewpingCreatePage;
