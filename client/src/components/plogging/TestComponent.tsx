import React, { useEffect, useRef } from "react";
import { GeolocationPosition } from "interface/ploggingInterface";
import useGPS from "./functions/useGPS";

const { naver } = window;

const TestComponent = () => {
  const { latitude, longitude, setOnSearch } = useGPS();
  const preventDup = useRef<boolean>(true);

  useEffect(() => {
    let isFailed = false;
    if (preventDup.current) {
      const getGPS = new Promise<GeolocationPosition>((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
      });

      getGPS
        .then((response) => {
          const { latitude, longitude } = response.coords;
          const map = new naver.maps.Map("map", {
            center: new naver.maps.LatLng(latitude, longitude),
            zoomControl: true,
          });
        })
        .catch((error) => {
          isFailed = true;
          console.error(error);
        });
    }

    return () => {
      if (preventDup.current) {
        if (isFailed) {
          alert(`GPS 정보를 불러오는데 실패했습니다.`);
          // 다른 페이지로 이동 등의 로직 설정
        } else {
          preventDup.current = false;
        }
      }
    };
  }, []);

  useEffect(() => {
    if (!preventDup) {
      if (typeof latitude === "number" && typeof longitude === "number") {
        const map = new naver.maps.Map("map", {
          center: new naver.maps.LatLng(latitude, longitude),
          zoomControl: true,
        });
      } else {
        alert(`이게 실패한다고!?`);
      }
    }
  }, [latitude, longitude]);

  return (
    // 추후 크기는 타협해야할 듯
    // https://velog.io/@kyungjune/naver-map-api-%EA%B8%B0%EC%96%B5%ED%95%98%EA%B8%B0react.ts
    // https://velog.io/@silverbeen/Naver-Map-%EC%9E%90%EC%9C%A0%EB%A1%AD%EA%B2%8C-%ED%99%9C%EC%9A%A9%ED%95%98%EA%B8%B0
    <div style={{ height: "calc(100vh - 56px)", width: "100%" }}>
      <div id="map" style={{ height: "100%", width: "100%" }}></div>;
    </div>
  );
};

export default TestComponent;
