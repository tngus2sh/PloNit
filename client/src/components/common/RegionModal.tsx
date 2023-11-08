import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import CommonButton from "./CommonButton";
import style from "styles/css/Common/RegionModal.module.css";
import { getSido, getGugun, getDong } from "api/lib/region";
import { RegionInterface } from "interface/regionInterface";

interface RegionModalProps {
  onClose: () => void;
  setSignupRegion: any;
  setRegionCode: any;
}

const RegionModal = ({
  onClose,
  setSignupRegion,
  setRegionCode,
}: RegionModalProps) => {
  const accessToken = useSelector((state: any) => state.user.accessToken);
  const [sidoData, setsidoData] = useState<RegionInterface[]>([]);
  const [selectedSidoCode, setSelectedSidoCode] = useState<number | null>(null);
  const [selectedSidoName, setSelectedSidoName] = useState<string | null>(null);
  const [gugunData, setgugunData] = useState<RegionInterface[]>([]);
  const [selectedGugunCode, setSelectedGugunCode] = useState<number | null>(
    null,
  );
  const [selectedGugunName, setSelectedGugunName] = useState<string | null>(
    null,
  );
  const [dongData, setdongData] = useState<RegionInterface[]>([]);
  const [selectedDongCode, setSelectedDongCode] = useState<number | null>(null);
  const [selectedDongName, setSelectedDongName] = useState<number | null>(null);

  useEffect(() => {
    getSido(
      accessToken,
      (res) => {
        console.log("시도 API 연결 성공");
        console.log(res.data.resultBody);
        setsidoData(res.data.resultBody);
      },
      (err) => {
        console.log("시도 API 연결 실패", err);
      },
    );
  }, []);
  console.log(sidoData);

  const SelectedSido = (item: any) => {
    setSelectedSidoCode(item.sidoCode);
    setSelectedSidoName(item.sidoName);
    getGugun(
      accessToken,
      item.sidoCode,
      (res) => {
        console.log("구군 API 연결 성공");
        console.log(res.data.resultBody);
        setgugunData(res.data.resultBody);
      },
      (err) => {
        console.log("구군 API 연결 실패", err);
      },
    );
  };
  const SelectedGugun = (item: any) => {
    setSelectedGugunCode(item.gugunCode);
    setSelectedGugunName(item.gugunName);
    getDong(
      accessToken,
      item.gugunCode,
      (res) => {
        console.log("동 API 연결 성공");
        console.log(res.data.resultBody);
        setdongData(res.data.resultBody);
      },
      (err) => {
        console.log("동 API 연결 실패", err);
      },
    );
  };

  const SelectedDong = (item: any) => {
    setSelectedDongCode(item.dongCode);
    setSelectedDongName(item.dongName);
    setSignupRegion(
      `${selectedSidoName} ${selectedGugunName} ${item.dongName}`,
    );
    setRegionCode(item.dongCode);
  };

  return (
    <div className={style.regionmodal}>
      <div className={style.regioncontainer}>
        <div className={style.top_section}>
          <div className={style.title}>시 / 도</div>
          <div className={style.title}>구 / 군</div>
          <div className={style.title}>동</div>
        </div>
        <div className={style.bottom_section}>
          <div className={style.sido}>
            {sidoData.map((item, index) => (
              <div
                key={index}
                className={`${style.content} ${
                  item.sidoCode === selectedSidoCode ? style.selected : ""
                }`}
                onClick={() => SelectedSido(item)}
              >
                {item.sidoName}
              </div>
            ))}
          </div>
          <div className={style.gugun}>
            {gugunData.map((item, index) => (
              <div
                key={index}
                className={`${style.content} ${
                  item.gugunCode === selectedGugunCode ? style.selected : ""
                }`}
                onClick={() => SelectedGugun(item)}
              >
                {item.gugunName}
              </div>
            ))}
          </div>
          <div className={style.dong}>
            {dongData.map((item, index) => (
              <div
                key={index}
                className={`${style.content} ${
                  item.dongCode === selectedDongCode ? style.selected : ""
                }`}
                onClick={() => SelectedDong(item)}
              >
                {item.dongName}
              </div>
            ))}
          </div>
        </div>
      </div>
      <div className={style.confirm_btn} onClick={onClose}>
        확인
      </div>
    </div>
  );
};

export default RegionModal;
