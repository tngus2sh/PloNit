import React from "react";
import style from "styles/css/PloggingPage/PloggingInfo.module.css";

interface PloggingInfoProps {
  infoLabel: string;
  infoValue: number | string;
}

const PloggingInfo: React.FC<PloggingInfoProps> = ({
  infoLabel,
  infoValue,
}) => {
  return (
    <div className={style.PloggingInfo}>
      <div className={style.up}>{infoLabel}</div>
      <div className={style.down}>{infoValue}</div>
    </div>
  );
};

export default PloggingInfo;
