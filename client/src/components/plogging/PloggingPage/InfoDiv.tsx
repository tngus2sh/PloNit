import React from "react";
import style from "styles/css/PloggingPage/SubComponent.module.css";
import Swal from "sweetalert2";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

interface IInfoTop {
  infoLabel: string;
  infoValue: number | string;
}

interface IIconBottom {
  icon: string;
  backgroundSize: string;
  onClick?: () => void;
}

function formatNumber(n: number): string {
  if (n < 10) {
    return "0" + n;
  }

  return n.toString();
}

const InfoTop: React.FC<IInfoTop> = ({ infoLabel, infoValue }) => {
  return (
    <div className={style.InfoTop}>
      <div className={style.up}>{infoLabel}</div>
      <div className={style.down}>{infoValue}</div>
    </div>
  );
};

const IconBottom: React.FC<IIconBottom> = ({
  icon,
  backgroundSize,
  onClick,
}) => {
  return (
    <div className={style.IconBottom}>
      <div
        className={style.icon}
        style={{
          backgroundImage: `url("${icon}")`,
          backgroundSize: backgroundSize,
        }}
        onClick={onClick}
      ></div>
    </div>
  );
};

const InfoDiv = ({ infoDivHeight }: { infoDivHeight: number }) => {
  const distance = useSelector<rootState, number>((state) => {
    const { distance } = state.plogging;
    return Math.round(distance * 100) / 100;
  });
  const second = useSelector<rootState, number>((state) => {
    const { second } = state.plogging;
    return second;
  });
  const minute = useSelector<rootState, number>((state) => {
    const { minute } = state.plogging;
    return minute;
  });
  const calorie = useSelector<rootState, number>((state) => {
    const { calorie } = state.plogging;
    return calorie;
  });

  return (
    <div style={{ height: `${infoDivHeight}px`, width: "100%" }}>
      <div style={{ height: "10%", width: "100%" }}></div>
      <div style={{ height: "40%", width: "100%", display: "flex" }}>
        <InfoTop infoLabel="km" infoValue={distance} />
        <InfoTop
          infoLabel="시간"
          infoValue={`${minute}:${formatNumber(second)}`}
        />
        <InfoTop infoLabel="칼로리" infoValue={calorie} />
      </div>
      <div style={{ height: "50%", width: "100%", display: "flex" }}>
        <IconBottom
          icon="images/PloggingPage/help-solid.svg"
          backgroundSize="50%"
        />
        <IconBottom
          icon="images/PloggingPage/stop-green.svg"
          backgroundSize="contain"
        />
        <IconBottom
          icon="images/PloggingPage/camera-solid.svg"
          backgroundSize="50%"
        />
      </div>
    </div>
  );
};

export default InfoDiv;
