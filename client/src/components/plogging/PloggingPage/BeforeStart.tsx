import React from "react";
import DefaultMap from "../DefaultMap";
import Swal from "sweetalert2";
import CommonButton from "components/common/CommonButton";

import { useSelector } from "react-redux";
import { rootState } from "store/store";

const BtnDiv = ({
  height,
  cbFunction,
}: {
  height: number;
  cbFunction?: () => void;
}) => {
  return (
    <div
      style={{
        height: `${height}px`,
        width: `100%`,
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <CommonButton
        text="시작하기"
        styles={{ backgroundColor: "#2cd261" }}
        onClick={cbFunction}
      />
    </div>
  );
};

function cbFunction() {
  Swal.fire({
    title: "it works!",
  });
}

const BeforeStart = () => {
  const btnDivHeight = useSelector<rootState, number>((state) => {
    const windowHeight = state.windowHeight.value;
    return windowHeight * 0.1;
  });

  return (
    <DefaultMap subHeight={btnDivHeight} isBefore={true}>
      <BtnDiv height={btnDivHeight} cbFunction={cbFunction}></BtnDiv>
    </DefaultMap>
  );
};

export default BeforeStart;
