import { statSync } from "fs";
import React from "react";
import style from "styles/css/CrewCommunityPage/Notice.module.css";
import { CrewInterface } from "interface/crewInterface";

const Notice = ({ crew }: { crew: CrewInterface }) => {
  return (
    <div className={style.notice_compo}>
      <div className={style.notice_title}>공지사항</div>
      <div className={style.notice_content}>
        {crew.notice ? (
          <>
            <div className={style.notice_upper}>
              <img src={crew.crewMasterProfileImage} alt="프로필" />
              <div className={style.nickname}>{crew.crewMasterNickname}</div>
            </div>
            <div className={style.notice_down}>
              <div>{crew.notice}</div>
            </div>
          </>
        ) : (
          <div style={{ padding: "0.5rem" }}>등록된 공지사항이 없습니다.</div>
        )}
      </div>
    </div>
  );
};

export default Notice;
