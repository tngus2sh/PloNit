import React, { useState, useEffect } from "react";
import AddressModal from "components/CrewpingCreate/AddressModal";
import style from "styles/css/CrewpingCreatePage.module.css";

const CrewpingAddress = ({ setCrewpingPlace }: any) => {
  const [isAddressModalOpen, setAddressModalOpen] = useState(false);
  // 주소 모달에서 바로 저장되는 주소
  const [selectedAddress, setSelectedAddress] = useState<{
    roadAddress: string | null;
    jibunAddress: string | null;
  }>({ roadAddress: null, jibunAddress: null });
  // 기본 주소
  const [addressInput, setAddressInput] = useState("");
  // 상세 주소
  const [addressDetailInput, setAddressDetailInput] = useState("");

  const handleAddressInputChange = (event: any) => {
    const newAddress = event.target.value;
    setAddressInput(newAddress);
    setCrewpingPlace(newAddress + " " + addressDetailInput);
  };
  const handleAddressDetailInputChange = (e: any) => {
    const newAddressDetail = e.target.value;
    setAddressDetailInput(newAddressDetail);
    setCrewpingPlace(addressInput + " " + newAddressDetail);
  };

  const handleAddressSelected = (addressData: any) => {
    setSelectedAddress(addressData);
    setAddressInput(addressData.roadAddress || addressData.jibunAddress || "");
    setAddressModalOpen(false);
  };
  useEffect(() => {
    const body = document.body;

    // 모달이 열려 있을 때 스크롤 비활성화
    if (isAddressModalOpen) {
      body.style.overflow = "hidden";
    } else {
      // 모달이 닫혔을 때 스크롤 활성화
      body.style.overflow = "auto";
    }

    // 효과 정리
    return () => {
      body.style.overflow = "auto";
    };
  }, [isAddressModalOpen]);

  return (
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
          onChange={handleAddressInputChange}
          onClick={() => setAddressModalOpen(true)}
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
        value={addressDetailInput}
        onChange={handleAddressDetailInputChange}
      />
      {isAddressModalOpen && (
        <AddressModal
          onClose={() => setAddressModalOpen(false)}
          onAddressSelected={handleAddressSelected}
        />
      )}
    </div>
  );
};

export default CrewpingAddress;
