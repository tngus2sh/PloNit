import React, { useState } from "react";
import Input from "components/common/Input";
import { BackTopBar } from "components/common/TopBar";
import AddressModal from "components/CrewpingCreate/AddressModal";
import DateTimeModal from "components/CrewpingCreate/DateTimeModal";
import style from "styles/css/CrewpingCreatePage.module.css";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const CrewpingCreatePage = () => {
  // 주소
  const [isAddressModalOpen, setAddressModalOpen] = useState(false);
  const [selectedAddress, setSelectedAddress] = useState<{
    roadAddress: string | null;
    jibunAddress: string | null;
  }>({ roadAddress: null, jibunAddress: null });
  const [addressInput, setAddressInput] = useState("");

  const handleAddressSelected = (addressData: any) => {
    setSelectedAddress(addressData);

    setAddressInput(addressData.roadAddress || addressData.jibunAddress || "");
    setAddressModalOpen(false);
  };

  //시간
  const [selectedDate, setSelectedDate] = useState<Date | null>(null);

  return (
    <div>
      <BackTopBar text="크루핑 생성" />
      <Input id="crewping_name" labelTitle="크루핑 이름" type="text" />
      <div className={style.address_area}>
        <div className={style.first_area}>
          <label className={style.label} htmlFor="address">
            주소
          </label>
        </div>
        <div className={style.second_area}>
          <input
            type="text"
            name="address"
            id="address"
            className={style.inputBox}
            readOnly={true}
            value={addressInput}
            onChange={(e) => setAddressInput(e.target.value)}
          />
          <button
            className={style.address_btn}
            onClick={() => setAddressModalOpen(true)}
          >
            주소검색
          </button>
        </div>
        <input
          type="text"
          name="address_detail"
          id="address_detail"
          placeholder="상세 주소"
          className={style.inputBox_detail}
        />
      </div>
      <div className={style.date_time}>
        <label className={style.label} htmlFor="date_time">
          시간
        </label>
        <DatePicker
          selected={selectedDate}
          className={style.inputBox}
          name="date_time"
          id="date_time"
          onChange={(date) => setSelectedDate(date)}
          dateFormat="yyyy.MM.dd"
        />
      </div>

      <div className={style.person_area}>
        <label className={style.label} htmlFor="person">
          모집 인원
        </label>
        <div className={style.person_content}>
          <input
            type="text"
            name="person"
            id="person"
            className={style.inputBox}
          />
          <div className={style.slash}>/</div>
          <div className={style.limit}>10</div>
        </div>
      </div>

      {isAddressModalOpen && (
        <AddressModal
          onClose={() => setAddressModalOpen(false)}
          onAddressSelected={handleAddressSelected}
        />
      )}
    </div>
  );
};

export default CrewpingCreatePage;
