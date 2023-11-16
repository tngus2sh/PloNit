import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { BackTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
import style from "styles/css/CrewNoticePage.module.css";
import { getNotice, getCrewDetail } from "api/lib/crew";
import { OkModal, NotOkModal } from "components/common/AlertModals";

const CrewNoticePage = () => {
  const navigate = useNavigate();
  const accessToken = useSelector((state: any) => state.user.auth.accessToken);
  const { crewId } = useParams();
  const [isNotice, setNotice] = useState("");

  const onChangeNotice = (event: any) => {
    setNotice(event.target.value);
  };

  useEffect(() => {
    getCrewDetail(
      accessToken,
      Number(crewId),
      (res) => {
        // console.log("크루 상세 조회 성공");
        // console.log(res.data.resultBody);
        setNotice(res.data.resultBody.notice);
      },
      (err) => {
        console.log("크루 상세 조회 실패", err);
      },
    );
  }, []);

  const SendNotice = () => {
    const data = {
      crewId: Number(crewId),
      content: isNotice,
    };
    getNotice(
      accessToken,
      data,
      (res) => {
        // console.log("공지사항 변경 성공");
        // console.log(res.data.resultBody);
        OkModal({ text: "공지사항이 변경되었습니다." });
        navigate(`/crew/community/${crewId}`);
      },
      (err) => {
        console.log("공지사항 변경 실패", err);
        NotOkModal({ text: "공지사항이 변경에 실패했습니다." });
      },
    );
  };

  return (
    <div>
      <BackTopBar text="공지사항" />
      <div className={style.crew_notice}>
        <label className={style.label} htmlFor="notice">
          공지사항
        </label>
        <textarea
          className={style.inputBox}
          name="notice"
          id="notice"
          placeholder="내용을 작성해주세요"
          value={isNotice}
          onChange={onChangeNotice}
        ></textarea>
      </div>
      <CommonButton
        text="수정하기"
        styles={{
          backgroundColor: "#2cd261",
        }}
        onClick={SendNotice}
      />
    </div>
  );
};

export default CrewNoticePage;
