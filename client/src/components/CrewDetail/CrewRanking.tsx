import React from "react";
import style from "styles/css/CrewDetailPage/CrewRanking.module.css";
import { CrewInterface } from "interface/crewInterface";

const formattedSeason = (datestr: any) => {
  const date = new Date(datestr);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const season = day === 1 ? 1 : 2;

  return `${year}년 ${month}월 ${season}시즌`;
};

const CrewRanking = ({ crew }: { crew: CrewInterface }) => {
  return (
    <div className={style.crew_ranking}>
      <div className={style.title}>크루 랭킹</div>
      <div className={style.date}>{formattedSeason(crew.startDate)}</div>
      <div className={style.contaniner}>
        <div className={style.ranking}>
          <div className={style.part}>전체</div>

          <div className={style.rank}>
            <span className={style.large}>{crew.totalRanking}</span>위
          </div>
          <div className={style.dist}>
            <span className={style.large}>{crew.totalDistance}</span>km
          </div>
        </div>
        <div className={style.ranking}>
          <div className={style.part}>평균</div>
          <div className={style.rank}>
            <span className={style.large}>{crew.avgRanking}</span>위
          </div>
          <div className={style.dist}>
            <span className={style.large}>{crew.avgDistance}</span>km
          </div>
        </div>
      </div>
    </div>
  );
};

export default CrewRanking;
