import React from "react";
import style from "styles/css/MyRankPage/MyRankItem.module.css";
import StarRoundedIcon from "@mui/icons-material/StarRounded";
import { MyRankInterface } from "interface/rankInterface";

const formattedSeason = (datestr: any) => {
  const date = new Date(datestr);
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const season = day === 1 ? 1 : 2;

  return `${month}-${season} 시즌`;
};

const MyRankItem = ({ rank }: { rank: MyRankInterface }) => {
  return (
    <div className={style.rank_item_container}>
      <div className={style.rank_title_container}>
        <span className={style.rank}>{rank.ranking}</span>위
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
            <span>{rank.distance}km</span>
          </div>
        </div>
      </div>

      {/* <div className={style.rank_detail}>
        <div className={style.season}>10월 1일~10월 15일(10-1시즌)</div>
        <div className={style.dist}>
          <span className={style.large}>8.54</span>km
        </div>
      </div> */}
    </div>
  );
};

export default MyRankItem;
