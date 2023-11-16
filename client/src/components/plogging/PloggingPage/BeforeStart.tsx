import React, { useState } from "react";
import DefaultMap from "../DefaultMap";
import BottomUpModal from "../ploggingComps/BottomUpModal";
import CommonButton from "components/common/CommonButton";
import getGPS from "../functions/getGPS";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";
import * as Crewping from "store/crewping-slice";

import { startPlogging } from "api/lib/plogging";

interface IBtnDiv {
  height: number;
  onClick?: () => void;
}

interface IPopUp {
  nickname?: string;
  onClick1?: () => void;
  onClick2?: () => void;
}

const BtnDiv: React.FC<IBtnDiv> = ({ height, onClick }) => {
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
        onClick={onClick}
      />
    </div>
  );
};

const PopUp: React.FC<IPopUp> = ({ nickname, onClick1, onClick2 }) => {
  const styles = {
    backgroundColor: "#2cd261",
    fontWeight: "bolder",
    // boxShadow: "0px 0px 10px 0px #1e7d2f",
    borderRadius: "0.8rem",
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
      <CommonButton text="자유 플로깅" styles={styles} onClick={onClick1} />
      <CommonButton text="봉사 플로깅" styles={styles} onClick={onClick2} />
    </div>
  );
};

const BeforeStart = () => {
  const btnDivHeight = useSelector<rootState, number>((state) => {
    const windowHeight = state.window.height;
    return windowHeight * 0.1;
  });
  const nickname = useSelector<rootState, string>((state) => {
    return state.user.info.nickname;
  });
  const accessToken = useSelector<rootState, string>((state) => {
    return state.user.auth.accessToken;
  });
  const weight = useSelector<rootState, number>((state) => {
    return state.user.info.weight;
  });
  const [show, setShow] = useState<boolean>(false);
  const [preventShow, setPreventShow] = useState<boolean>(false);

  const dispatch = useDispatch();
  async function onClick1() {
    dispatch(P.clear());
    dispatch(Crewping.clear());
    dispatch(P.setPloggingType("IND"));
    if (weight > 0) {
      dispatch(P.setKg(weight));
    }
    getGPS()
      .then((response) => {
        const { latitude, longitude } = response.coords;
        startPlogging({
          accessToken: accessToken,
          type: "IND",
          latitude: latitude,
          longitude: longitude,
          success: (response) => {
            dispatch(P.setPloggingId(response.data.resultBody));
          },
          fail: (error) => {
            console.error(error);
          },
        });
      })
      .catch((error) => {
        console.error(error);
      });
  }
  async function onClick2() {
    dispatch(P.clear());
    dispatch(Crewping.clear());
    dispatch(P.setPloggingType("VOL"));
    if (weight > 0) {
      dispatch(P.setKg(weight));
    }
    getGPS().then((response) => {
      const { longitude, latitude } = response.coords;
      startPlogging({
        accessToken: accessToken,
        type: "VOL",
        latitude: latitude,
        longitude: longitude,
        success: (response) => {
          dispatch(P.setPloggingId(response.data.resultBody));
        },
        fail: (error) => {
          console.error(error);
        },
      });
    });
  }

  return (
    <>
      <DefaultMap subHeight={btnDivHeight} isBefore={true}>
        <BtnDiv
          height={btnDivHeight}
          onClick={() => {
            setPreventShow(true);
            setShow(true);
          }}
        />
      </DefaultMap>
      {preventShow && (
        <BottomUpModal show={show} setShow={setShow}>
          <PopUp nickname={nickname} onClick1={onClick1} onClick2={onClick2} />
        </BottomUpModal>
      )}
    </>
  );
};

export default BeforeStart;
