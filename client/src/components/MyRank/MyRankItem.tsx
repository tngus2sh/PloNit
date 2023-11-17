import React from "react";
import style from "styles/css/MyRankPage/MyRankItem.module.css";
import StarRoundedIcon from "@mui/icons-material/StarRounded";
import { MyRankInterface } from "interface/rankInterface";

function roundToTwoDecimalPlaces(num: any) {
  return parseFloat(num.toFixed(2));
}

const formattedSeason = (datestr: any) => {
  const date = new Date(datestr);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const season = day === 1 ? 1 : 2;

  return `${year}년 ${month}월 ${season}시즌`;
};

const MyRankItem = ({ rank }: { rank: MyRankInterface }) => {
  return (
    <div className={style.rank_item_container}>
      <div className={style.rank_title_container}>
        {rank.ranking === 0 ? (
          <span className={style.rank}>{"-"}</span>
        ) : (
          <span className={style.rank}>{rank.ranking}</span>
        )}
        위
      </div>

      <div className={style.rank_info_container}>
        <div className={style.date_container}>
          <StarRoundedIcon sx={{ fontSize: "1.1rem" }} />
          &nbsp;
          <span className={style.season}>
            {formattedSeason(rank.startDate)}
          </span>
        </div>
        <div className={style.distance_container}>
          <div className={style.distance}>
            <span>{roundToTwoDecimalPlaces(rank.distance)}km</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MyRankItem;
