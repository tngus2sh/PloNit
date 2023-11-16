import React from "react";
import style from "styles/css/Common/CommonButton.module.css";

interface ButtonProps {
  text: string;
  onClick?: () => void;
  styles?: { [key: string]: string };
  id?: string;
  textColor?: string;
}

const CommonButton = ({
  text,
  onClick,
  styles,
  id,
  textColor,
}: ButtonProps) => {
  return (
    <div id={`${id}`}>
      <div className={style.common_btn} onClick={onClick} style={styles}>
        <div
          className={style.content}
          style={{ color: textColor ?? "#FFFFFF" }}
        >
          {text}
        </div>
      </div>
    </div>
  );
};

export default CommonButton;
