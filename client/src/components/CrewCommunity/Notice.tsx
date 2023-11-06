import { statSync } from "fs";
import React from "react";
import style from "styles/css/CrewCommunityPage/Notice.module.css";
import { CrewInterface } from "interface/crewInterface";

const Notice = ({ crew }: { crew: CrewInterface }) => {
  return (
    <div className={style.notice_compo}>
      <div className={style.notice_title}>공지사항</div>
      <div className={style.notice_content}>
        <div className={style.notice_upper}>
          <img src="/metamong.png" alt="몽" />
          <div className={style.nickname}>메타몽</div>
          <div className={style.date}>10월 10일</div>
        </div>
        <div className={style.notice_down}>
          <div className={style.imp}>중요</div>
          <div>모두 즐거운 플로깅 되세요!</div>
        </div>
      </div>
    </div>
  );
};

export default Notice;
