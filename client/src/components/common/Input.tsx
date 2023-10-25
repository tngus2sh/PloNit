import React from "react";
import style from "styles/css/Common/Input.module.css";

interface InputProps {
  id?: string;
  type: string;
  value?: string;
  onChange?: any;
  styles?: { [key: string]: string };
  labelTitle: string | "";
  required?: boolean;
  disabled?: boolean;
}

const Input = ({
  value,
  onChange,
  id,
  styles,
  labelTitle,
  type,
  required,
  disabled,
}: InputProps) => {
  return (
    <div className={style.input_area} style={styles}>
      <label className={style.label} htmlFor={id}>
        {labelTitle}
      </label>
      <input
        className={style.inputBox}
        type={type}
        value={value}
        onChange={onChange}
        id={id}
        name={id}
        placeholder=""
        required={required}
        disabled={disabled}
      />
    </div>
  );
};

export default Input;
