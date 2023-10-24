import React, { useState, useEffect, useRef } from "react";
import { GeolocationPosition } from "interface/ploggingInterface";
import useGPS from "./functions/useGPS";
import style from "styles/css/PloggingPage/DefaultMap.module.css";

import { dummy_toilets } from "./dummyData";
import { current } from "@reduxjs/toolkit";

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

const DefaultMap = () => {
  const { latitude, longitude, setOnSearch } = useGPS();
  const preventDup = useRef<boolean>(true);
  const mapRef = useRef<naver.maps.Map | null>(null);
  const userRef = useRef<naver.maps.Marker | null>(null);
  const centerBtnRef = useRef<naver.maps.CustomControl | null>(null);
  const binBtnRef = useRef<naver.maps.CustomControl | null>(null);
  const toiletBtnRef = useRef<naver.maps.CustomControl | null>(null);
  const [isCentered, setIsCentered] = useState<boolean>(false);
  const [showIcon, setShowIcon] = useState<boolean>(true);
  const [showBin, setShowBin] = useState<boolean>(false);
  const [showToilet, setShowToilet] = useState<boolean>(false);
  const [bins, setBins] = useState<Coordinate[]>([]);
  const [toilets, setToilets] = useState<Coordinate[]>([]);
  const binMarkers: naver.maps.Marker[] = [];
  const toiletMarkers: naver.maps.Marker[] = [];

  const centerBtn = isCentered
    ? `<div class="${style.btn_white_margin_inc}" style="background-image:url('images/PloggingPage/location-cross-blue.svg')"></div>`
    : `<div class="${style.btn_white_margin_inc}" style="background-image:url('images/PloggingPage/location-cross-black.svg')"></div>`;
  const binBtn = showBin
    ? `<div class="${style.btn_green_margin_inc}" style="background-image:url('images/PloggingPage/trash-solid.svg')"></div>`
    : `<div class="${style.btn_black_margin_inc}" style="background-image:url('images/PloggingPage/trash-solid.svg')"></div>`;
  const toiletBtn = showToilet
    ? `<div class="${style.btn_blue}" style="background-image:url('images/PloggingPage/toilet-solid.svg')"></div>`
    : `<div class="${style.btn_black}" style="background-image:url('images/PloggingPage/toilet-solid.svg')"></div>`;

  useEffect(() => {
    let isFailed = false;
    if (preventDup.current) {
      const getGPS = new Promise<GeolocationPosition>((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
      });

      getGPS
        .then((response) => {
          setIsCentered(true);
          setToilets(dummy_toilets);
          console.log(dummy_toilets);
          const { latitude, longitude } = response.coords;
          console.log(latitude);
          console.log(longitude);
          const map = new naver.maps.Map("map", {
            center: new naver.maps.LatLng(latitude, longitude),
            zoom: 16,
          });
          mapRef.current = map;

          const user = new naver.maps.Marker({
            position: new naver.maps.LatLng(latitude, longitude),
            map: mapRef.current,
            icon: {
              url: `images/PloggingPage/myLocation.svg`,
              size: new naver.maps.Size(35, 35),
              scaledSize: new naver.maps.Size(35, 35),
              origin: new naver.maps.Point(0, 0),
              anchor: new naver.maps.Point(17.5, 17.5),
            },
          });
          userRef.current = user;

          const centerBtnIndicator = new naver.maps.CustomControl(centerBtn, {
            position: naver.maps.Position.LEFT_BOTTOM,
          });
          centerBtnRef.current = centerBtnIndicator;
          const binBtnIndicator = new naver.maps.CustomControl(binBtn, {
            position: naver.maps.Position.RIGHT_BOTTOM,
          });
          binBtnRef.current = binBtnIndicator;
          const toiletBtnIndicator = new naver.maps.CustomControl(toiletBtn, {
            position: naver.maps.Position.RIGHT_BOTTOM,
          });
          toiletBtnRef.current = toiletBtnIndicator;

          naver.maps.Event.once(mapRef.current, "init", () => {
            centerBtnIndicator.setMap(mapRef.current);
            binBtnIndicator.setMap(mapRef.current);
            toiletBtnIndicator.setMap(mapRef.current);

            naver.maps.Event.addDOMListener(
              binBtnIndicator.getElement(),
              "click",
              () => {
                setShowBin((current) => {
                  return !current;
                });
              },
            );
            naver.maps.Event.addDOMListener(
              toiletBtnIndicator.getElement(),
              "click",
              () => {
                setShowToilet((current) => {
                  return !current;
                });
              },
            );
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
    if (!preventDup.current) {
      if (showBin) {
        binMarkers.forEach((binMarker) => {
          binMarker.setMap(mapRef.current);
        });
      } else {
        binMarkers.length = 0;
        bins.forEach((bin) => {
          binMarkers.push(
            new naver.maps.Marker({
              position: new naver.maps.LatLng(bin.latitude, bin.longitude),
              map: mapRef.current ?? undefined,
              icon: {
                url: `images/PloggingPage/bin-icon.png`,
                size: new naver.maps.Size(35, 35),
                scaledSize: new naver.maps.Size(35, 35),
                origin: new naver.maps.Point(0, 0),
                anchor: new naver.maps.Point(17.5, 17.5),
              },
            }),
          );
        });
      }

      if (showToilet) {
        toilets.forEach((toilet) => {
          toiletMarkers.push(
            new naver.maps.Marker({
              position: new naver.maps.LatLng(
                toilet.latitude,
                toilet.longitude,
              ),
              map: mapRef.current ?? undefined,
              icon: {
                url: `images/PloggingPage/toilet-icon.png`,
                size: new naver.maps.Size(35, 35),
                scaledSize: new naver.maps.Size(35, 35),
                origin: new naver.maps.Point(0, 0),
                anchor: new naver.maps.Point(17.5, 17.5),
              },
            }),
          );
        });
      } else {
        toiletMarkers.forEach((toiletMarker) => {
          toiletMarker.setMap(null);
        });
      }
    }
  }, [bins, toilets, showBin, showToilet]);

  // useEffect(() => {
  //   if (!preventDup.current) {
  //   }
  // }, []);

  return (
    <div style={{ height: "calc(100vh - 56px)", width: "100%" }}>
      <div id="map" style={{ height: "100%", width: "100%" }}></div>
    </div>
  );
};

export default DefaultMap;
