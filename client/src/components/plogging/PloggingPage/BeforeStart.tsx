import React from "react";
import DefaultMap from "../DefaultMap";
import Swal from "sweetalert2";
import CommonButton from "components/common/CommonButton";
import { renderToString } from "react-dom/server";
import useGPS from "../functions/useGPS";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import { clear, setPloggingType, setCbURL } from "store/plogging-slice";

interface IBtnDiv {
  height: number;
  cbFunction?: () => void;
}

interface IPopUp {
  nickname?: string;
}

interface IcbFunction {
  nickname: string;
  onClick1?: () => void;
  onClick2?: () => void;
}

const BtnDiv: React.FC<IBtnDiv> = ({ height, cbFunction }) => {
  return (
    <div
      style={{
        height: `${height}px`,
        width: `100%`,
        transition: `all 200ms ease-in-out`,
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

const PopUp: React.FC<IPopUp> = ({ nickname }) => {
  const styles = {
    backgroundColor: "#999999",
    fontWeight: "bolder",
    boxShadow: "3px 3px 3px #000000",
  };

  const TextComponent = () => {
    return (
      <div style={{ marginLeft: "1rem" }}>
        <div style={{ textAlign: "start" }}>
          <span
            style={{
              fontWeight: "bolder",
              fontSize: "1.5rem",
              marginBlockStart: 0,
            }}
          >
            {nickname}
          </span>
          <span>님,</span>
        </div>
        <div style={{ textAlign: "start" }}>플로깅 유형을 선택해주세요.</div>
      </div>
    );
  };

  return (
    <div>
      <TextComponent />
      <CommonButton
        text="자유 플로깅"
        styles={styles}
        id="beforeStart-commonBtn1"
      />
      <CommonButton
        text="봉사 플로깅"
        styles={styles}
        id="beforeStart-commonBtn2"
      />
    </div>
  );
};

function cbFunction({ nickname, onClick1, onClick2 }: IcbFunction) {
  function cbOnClick1() {
    if (onClick1) {
      onClick1();
    }
    Swal.close();
  }
  function cbOnClick2() {
    if (onClick2) {
      onClick2();
    }
    Swal.close();
  }

  Swal.fire({
    position: "bottom",
    html: renderToString(<PopUp nickname={nickname} />),
    showConfirmButton: false,
    showClass: {
      popup: "animate__animated animate__slideInUp",
    },
    hideClass: {
      popup: "animate__animated animate__slideOutDown",
    },
    willOpen: () => {
      const Btn1 = document.querySelector("#beforeStart-commonBtn1");
      const Btn2 = document.querySelector("#beforeStart-commonBtn2");
      if (Btn1 && onClick1) {
        Btn1.addEventListener("click", cbOnClick1);
      }
      if (Btn2 && onClick2) {
        Btn2.addEventListener("click", cbOnClick2);
      }
    },
    didClose: () => {
      const Btn1 = document.querySelector("#beforeStart-commonBtn1");
      const Btn2 = document.querySelector("#beforeStart-commonBtn2");
      if (Btn1 && onClick1) {
        Btn1.removeEventListener("click", cbOnClick1);
      }
      if (Btn2 && onClick2) {
        Btn2.removeEventListener("click", cbOnClick2);
      }
    },
  });
}

const BeforeStart = () => {
  const btnDivHeight = useSelector<rootState, number>((state) => {
    const windowHeight = state.window.height;
    return windowHeight * 0.1;
  });
  const nickname = useSelector<rootState, string>((state) => {
    return state.user.nickname;
  });
  const { latitude, longitude } = useGPS(); // 시작 시 GPS 정보 전달

  const dispatch = useDispatch();
  function onClick1() {
    dispatch(clear());
    dispatch(setPloggingType("IND"));
    dispatch(setCbURL("/plogging"));
  }
  function onClick2() {
    dispatch(clear());
    dispatch(setPloggingType("VOL"));
    dispatch(setCbURL("/plogging"));
  }

  return (
    <DefaultMap subHeight={btnDivHeight} isBefore={true}>
      <BtnDiv
        height={btnDivHeight}
        cbFunction={() => {
          cbFunction({
            nickname: nickname,
            onClick1: onClick1,
            onClick2: onClick2,
          });
        }}
      />
    </DefaultMap>
  );
};

export default BeforeStart;
