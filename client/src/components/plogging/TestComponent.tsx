import React, { useState, useEffect, useRef } from "react";
import { GeolocationPosition } from "interface/ploggingInterface";
import useGPS from "./functions/useGPS";
import style from "styles/css/TestComponent.module.css";

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

// 아이콘 사이즈를 35 35로 지정할 것

interface Coordinate {
  latitude: number;
  longitude: number;
}

const TestComponent = () => {
  const { latitude, longitude, setOnSearch } = useGPS();
  const preventDup = useRef<boolean>(true);
  const mapRef = useRef<naver.maps.Map | null>(null);
  const userRef = useRef<naver.maps.Marker | null>(null);
  const centerRef = useRef<naver.maps.CustomControl | null>(null);
  const binRef = useRef<naver.maps.CustomControl | null>(null);
  const toiletRef = useRef<naver.maps.CustomControl | null>(null);
  const [isCentered, setIsCentered] = useState<boolean>(false);
  const [showIcon, setShowIcon] = useState<boolean>(false);
  const [binIcon, setBinIcon] = useState<boolean>(false);
  const [toiletIcon, setToiletIcon] = useState<boolean>(false);
  const [bins, setBins] = useState<Coordinate[]>([]);
  const [toilets, setToilets] = useState<Coordinate[]>([]);

  const centerBtnBlack = `<div class="${style.btn}" style="background-image:url('images/location-cross-black.svg')"></div>`;
  const centerBtnBlue = `<div class="${style.btn}" style="background-image:url('images/location-cross-blue.svg')"></div>`;

  useEffect(() => {
    let isFailed = false;
    if (preventDup.current) {
      const getGPS = new Promise<GeolocationPosition>((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
      });

      getGPS
        .then((response) => {
          setShowIcon(true);
          const { latitude, longitude } = response.coords;
          console.log(latitude);
          console.log(longitude);
          mapRef.current = new naver.maps.Map("map", {
            center: new naver.maps.LatLng(latitude, longitude),
            zoom: 16,
          });

          userRef.current = new naver.maps.Marker({
            position: new naver.maps.LatLng(latitude, longitude),
            map: mapRef.current,
            icon: {
              url: `images/myLocation.svg`,
              size: new naver.maps.Size(35, 35),
              scaledSize: new naver.maps.Size(35, 35),
              origin: new naver.maps.Point(0, 0),
              anchor: new naver.maps.Point(17.5, 17.5),
            },
          });

          centerRef.current = new naver.maps.CustomControl(centerBtnBlack, {
            position: naver.maps.Position.LEFT_BOTTOM,
          });
          binRef.current = new naver.maps.CustomControl(centerBtnBlue, {
            position: naver.maps.Position.LEFT_BOTTOM,
          });

          naver.maps.Event.once(mapRef.current, "init", () => {
            centerRef.current?.setMap(mapRef.current);
            binRef.current?.setMap(mapRef.current);
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
        }
        preventDup.current = false;
      }
    };
  }, []);

  useEffect(() => {
    if (!preventDup) {
      console.log(`준비 중`);
      if (typeof latitude === "number" && typeof longitude === "number") {
        mapRef.current = new naver.maps.Map("map", {
          zoom: 16,
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
