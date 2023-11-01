import React from "react";
import { BackTopBar } from "components/common/TopBar";
import CommonButton from "components/common/CommonButton";
import style from "styles/css/FeedCreate.module.css";
import { Icon } from "@iconify/react";

const FeedCreatePage = () => {
  return (
    <div>
      <BackTopBar text="피드 생성" />
      <div className={style.photo}>
        <Icon icon="bi:camera" style={{ width: "3rem", height: "2.5rem" }} />
        <div>사진을 등록해주세요</div>
      </div>
      <div className={style.introduce}>
        <textarea
          className={style.inputBox}
          name="feed_introduce"
          id="feed_introduce"
          placeholder="내용을 작성해주세요"
        ></textarea>
      </div>
      <CommonButton
        text="피드 작성하기"
        styles={{
          backgroundColor: "#2cd261",
        }}
      />
    </div>
  );
};

export default FeedCreatePage;
