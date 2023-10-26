import React, { useState } from "react";
import AddressModal from "components/CrewpingCreate/AddressModal";
import style from "styles/css/CrewpingCreatePage.module.css";

const CrewpingAddress = () => {
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
          onChange={(e) => setAddressInput(e.target.value)}
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
