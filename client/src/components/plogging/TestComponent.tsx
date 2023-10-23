import React, { useEffect, useRef } from "react";
import useGPS from "./functions/useGPS";

const { naver } = window;

const TestComponent = () => {
  const { latitude, longitude, setOnSearch } = useGPS();
  const preventDup = useRef<boolean>(true);

  useEffect(() => {
    console.log(latitude);
    console.log(longitude);
    if (typeof latitude === "number" && typeof longitude === "number") {
      const map = new naver.maps.Map("map", {
        center: new naver.maps.LatLng(latitude, longitude),
        zoomControl: true,
      });
    } else {
      console.log(`GPS Loading...`);
    }
  }, [latitude, longitude]);

  return (
    // 추후 크기는 타협해야할 듯
    // https://velog.io/@kyungjune/naver-map-api-%EA%B8%B0%EC%96%B5%ED%95%98%EA%B8%B0react.ts
    // https://velog.io/@silverbeen/Naver-Map-%EC%9E%90%EC%9C%A0%EB%A1%AD%EA%B2%8C-%ED%99%9C%EC%9A%A9%ED%95%98%EA%B8%B0
    <div style={{ height: "915px", width: "412px" }}>
      <div id="map" style={{ height: "100%", width: "100%" }}></div>;
    </div>
  );
};

export default TestComponent;
