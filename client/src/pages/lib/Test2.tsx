import React from "react";
import useSocket from "components/plogging/functions/useSocket";

const Test2 = () => {
  const { locations, handleLocation } = useSocket({
    roomId: "test",
    senderId: "박주성",
  });
  return (
    <div>
      <button onClick={handleLocation}>위치 보내기</button>
    </div>
  );
};

export default Test2;
