import React from "react";
import style from "styles/css/CrewpingCreatePage/AddressModal.module.css";
import DaumPostcode from "react-daum-postcode";

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
      <DaumPostcode onComplete={handleComplete} animation autoClose={false} />
    </div>
  );
};

export default AddressModal;
