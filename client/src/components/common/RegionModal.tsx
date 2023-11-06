import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import CommonButton from "./CommonButton";
import style from "styles/css/AddInfoPage.module.css";
import { getSido, getGugun, getDong } from "api/lib/region";
import { RegionInterface } from "interface/regionInterface";

interface RegionModalProps {
  onClose: () => void;
}

const RegionModal = ({ onClose }: RegionModalProps) => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [sidoData, setsidoData] = useState<RegionInterface[]>([]);
  const [gugunData, setgugunData] = useState<RegionInterface[]>([]);
  const [dongData, setdongData] = useState<RegionInterface[]>([]);

  const [isSelectedSido, setSelectedSido] = useState("");

  useEffect(() => {
    getSido(
      accessToken,
      (res) => {
        console.log("시도 API 연결 성공");
        console.log(res);
        setsidoData(res.data.resultBody);
      },
      (err) => {
        console.log("시도 API 연결 실패", err);
      },
    );
  }, []);

  return (
    <div className={style.regionmodal}>
      <div className={style.regioncontainer}>
        <div className={style.sido}>
          <div className={style.title}>시 / 도</div>
          {sidoData.map((item) => (
            <div key={item.sidoCode}>{item.sidoName}</div>
          ))}
        </div>
        <div className={style.gugun}>
          <div className={style.title}>구 / 군</div>
        </div>
        <div className={style.dong}>
          <div className={style.title}>동</div>
        </div>
      </div>
      <div className={style.confirm_btn} onClick={onClose}>
        확인
      </div>
    </div>
  );
};

export default RegionModal;
