import React, { useEffect, useRef } from "react";
import { GeolocationPosition } from "interface/ploggingInterface";
import useGPS from "./functions/useGPS";

const { naver } = window;

// 대략적으로 필요한 기능들

// default useEffect에서 필요한 기능
// 유저 초기 위치 확인
// 청소된 구역 표시
// 플로깅 기록

// [latitude, longitude] useEffect에서 필요한 기능
// 유저 위치 표시
// 주변 유저 위치 표시
// 주변 쓰레기통 표시
// 주변 화장실 위치 표시
// 주변 유저 도움 요청 표시

const TestComponent = () => {
  const { latitude, longitude, setOnSearch } = useGPS();
  const preventDup = useRef<boolean>(true);
  const mapRef = useRef<naver.maps.Map | null>(null);
  const markerRef = useRef<naver.maps.Marker | null>(null);

  useEffect(() => {
    let isFailed = false;
    if (preventDup.current) {
      const getGPS = new Promise<GeolocationPosition>((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
      });

      getGPS
        .then((response) => {
          const { latitude, longitude } = response.coords;
          mapRef.current = new naver.maps.Map("map", {
            center: new naver.maps.LatLng(latitude, longitude),
            zoom: 16,
          });

          markerRef.current = new naver.maps.Marker({
            position: new naver.maps.LatLng(latitude, longitude),
            map: mapRef.current,
            icon: {
              url: ``,
              size: new naver.maps.Size(22, 35),
              origin: new naver.maps.Point(0, 0),
              anchor: new naver.maps.Point(11, 35),
            },
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
        mapRef.current = new naver.maps.Map("map", {
          center: new naver.maps.LatLng(latitude, longitude),
        });
      }
    }
  }, [latitude, longitude]);

  return (
    <div style={{ height: "calc(100vh - 56px)", width: "100%" }}>
      <div id="map" style={{ height: "100%", width: "100%" }}></div>;
    </div>
  );
};

export default TestComponent;
