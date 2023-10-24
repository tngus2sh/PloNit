import React from "react";
import style from "styles/css/CommonButton.module.css";

interface ButtonProps {
  text: string;
  onClick?: () => void;
  styles?: { [key: string]: string };
}

const CommonButton = ({ text, onClick, styles }: ButtonProps) => {
  return (
    <div>
      <div className={style.common_btn} onClick={onClick} style={styles}>
        <div className={style.content}>{text}</div>
      </div>
    </div>
  );
};

export default CommonButton;
