import React from "react";
import style from "styles/css/Common/TopBar.module.css";
import { useNavigate } from "react-router-dom";
import { Icon } from "@iconify/react";

export const LogoTopBar = () => {
  return (
    <div className={style.logoTopbar}>
      <img src="/plonit_logo2.png" alt="logo" />
    </div>
  );
};

export const BasicTopBar = ({
  text,
  styles,
}: {
  text: string;
  styles?: { [key: string]: string };
}) => {
  return (
    <div className={style.basicTopbar} style={{ ...styles }}>
      <div className={style.title_name}>{text}</div>
    </div>
  );
};

export const BackTopBar = ({ text }: { text: string }) => {
  const navigate = useNavigate();

  const goBackHandler = () => {
    navigate(-1);
  };

  return (
    <div className={style.backTopbar}>
      <div className={style.back_icon}>
        <Icon
          icon="bi:chevron-left"
          onClick={goBackHandler}
          style={{ width: "1.8rem", height: "1.8rem", marginLeft: "0.3rem" }}
        />
      </div>
      <div className={style.title_name}>{text}</div>
    </div>
  );
};
