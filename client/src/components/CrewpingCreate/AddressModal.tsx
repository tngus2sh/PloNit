import React from "react";
import style from "styles/css/CrewpingCreatePage/AddressModal.module.css";
import DaumPostcode from "react-daum-postcode";
import { BasicTopBar } from "components/common/TopBar";
import { Icon } from "@iconify/react";

interface AddressModalProps {
  onClose: () => void;
  onAddressSelected: (addressData: any) => void; // Use 'any' type for address data
}

const AddressModal: React.FC<AddressModalProps> = ({
  onClose,
  onAddressSelected,
}) => {
  const handleComplete = (data: any) => {
    onAddressSelected(data);
    onClose();
  };

  return (
    <div className={style.address_modal}>
      <div className={style.top_section}>
        <BasicTopBar text="주소 검색" />
        <Icon
          icon="bi:x"
          onClick={onClose}
          className={style.icon}
          style={{ width: "3rem", height: "3rem" }}
        />
      </div>
      <DaumPostcode onComplete={handleComplete} style={{ height: "100%" }} />
    </div>
  );
};

export default AddressModal;
