import React, { useState, useEffect, useRef } from "react";
import Swal from "sweetalert2";
import {
  GeolocationPosition,
  Coordinate,
  Help,
} from "interface/ploggingInterface";
import useGPS from "./functions/useGPS";
import useCluster from "./functions/useCluster";
import style from "styles/css/PloggingPage/DefaultMap.module.css";

import { dummy_location, dummy_helps } from "./dummyData";

import { naver } from "components/common/useNaver";

// 대략적으로 필요한 기능들

// default useEffect에서 필요한 기능
// 유저 초기 위치 확인 x
// 청소된 구역 표시
// 플로깅 기록

// [latitude, longitude] useEffect에서 필요한 기능
// 유저 위치 표시 x
// 주변 유저 위치 표시 x
// 주변 쓰레기통 표시 x
// 주변 화장실 위치 표시 x
// 주변 유저 도움 요청 표시 x

interface DefaultMap {
  subHeight: number;
}

const DefaultMap = ({ subHeight }: DefaultMap) => {
  const [isDefault, setIsDefault] = useState<boolean>(true);
  const [windowHeight, setWindowHeight] = useState<number>(window.innerHeight);
  const { latitude, longitude, onSearch, setOnSearch } = useGPS();
  const preventDup = useRef<boolean>(true);
  const mapRef = useRef<naver.maps.Map | null>(null);
  const userRef = useRef<naver.maps.Marker | null>(null);
  const centerBtnRef = useRef<naver.maps.CustomControl | null>(null);
  const binBtnRef = useRef<naver.maps.CustomControl | null>(null);
  const toiletBtnRef = useRef<naver.maps.CustomControl | null>(null);
  const [showIcon, setShowIcon] = useState<boolean>(true);
  const [showBin, setShowBin] = useState<boolean>(false);
  const [showToilet, setShowToilet] = useState<boolean>(false);
  const [bins, setBins] = useState<Coordinate[]>([]);
  const [toilets, setToilets] = useState<Coordinate[]>([]);
  const [neighbors, setNeighbors] = useState<Coordinate[]>([]);
  const [helps, setHelps] = useState<Help[]>([]);
  const binMarkers = useRef<naver.maps.Marker[]>([]);
  const toiletMarkers = useRef<naver.maps.Marker[]>([]);
  const neighborMarkers = useRef<naver.maps.Marker[]>([]);
  const helpMarkers = useRef<naver.maps.Marker[]>([]);
  const binCluster = useRef<any>(null);
  const toiletCluster = useRef<any>(null);

  const centerBtn_inactive = `<div class="${style.btn_white_margin_inc}" style="background-image:url('images/PloggingPage/location-cross-black.svg')"></div>`;
  const centerBtn_active = `<div class="${style.btn_white_margin_inc}" style="background-image:url('images/PloggingPage/location-cross-blue.svg')"></div>`;
  const binBtn_inactive = `<div class="${style.btn_black_margin_inc}" style="background-image:url('images/PloggingPage/trash-solid.svg')"></div>`;
  const binBtn_active = `<div class="${style.btn_green_margin_inc}" style="background-image:url('images/PloggingPage/trash-solid.svg')"></div>`;
  const toiletBtn_inactive = `<div class="${style.btn_black}" style="background-image:url('images/PloggingPage/toilet-solid.svg')"></div>`;
  const toiletBtn_active = `<div class="${style.btn_blue}" style="background-image:url('images/PloggingPage/toilet-solid.svg')"></div>`;

  // 초기맵 생성 및 기초 구조 구성
  useEffect(() => {
    let isFailed = false;
    if (preventDup.current) {
      const getGPS = new Promise<GeolocationPosition>((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
      });

      getGPS
        .then((response) => {
          // setBins(dummy_location);
          // setToilets(dummy_location);
          // setNeighbors(dummy_location);
          // setHelps(dummy_helps);
          const { latitude, longitude } = response.coords;
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
            cursor: "default",
          });
          userRef.current = user;

          const centerBtnIndicator_inactive = new naver.maps.CustomControl(
            centerBtn_inactive,
            {
              position: naver.maps.Position.LEFT_BOTTOM,
            },
          );
          const centerBtnIndicator_active = new naver.maps.CustomControl(
            centerBtn_active,
            {
              position: naver.maps.Position.LEFT_BOTTOM,
            },
          );
          const binBtnIndicator_inactive = new naver.maps.CustomControl(
            binBtn_inactive,
            {
              position: naver.maps.Position.RIGHT_BOTTOM,
            },
          );
          const binBtnIndicator_active = new naver.maps.CustomControl(
            binBtn_active,
            {
              position: naver.maps.Position.RIGHT_BOTTOM,
            },
          );
          const toiletBtnIndicator_inactive = new naver.maps.CustomControl(
            toiletBtn_inactive,
            {
              position: naver.maps.Position.RIGHT_BOTTOM,
            },
          );
          const toiletBtnIndicator_active = new naver.maps.CustomControl(
            toiletBtn_active,
            {
              position: naver.maps.Position.RIGHT_BOTTOM,
            },
          );

          naver.maps.Event.once(map, "init", () => {
            centerBtnIndicator_active.setMap(map);
            binBtnIndicator_inactive.setMap(map);
            toiletBtnIndicator_inactive.setMap(map);
            centerBtnRef.current = centerBtnIndicator_active;
            binBtnRef.current = binBtnIndicator_inactive;
            toiletBtnRef.current = toiletBtnIndicator_inactive;

            naver.maps.Event.addListener(map, "dragend", () => {
              centerBtnIndicator_active.setMap(null);
              centerBtnIndicator_inactive.setMap(map);
              centerBtnRef.current = centerBtnIndicator_inactive;
            });
            naver.maps.Event.addListener(map, "zoom_changed", () => {
              centerBtnIndicator_active.setMap(null);
              centerBtnIndicator_inactive.setMap(map);
              centerBtnRef.current = centerBtnIndicator_inactive;

              if (isDefault) {
                const currentZoom = map.getZoom();
                if (currentZoom <= 12) {
                  neighborMarkers.current.forEach((neighborMarker) => {
                    neighborMarker.setMap(null);
                  });
                  helpMarkers.current.forEach((helpMarker) => {
                    helpMarker.setMap(null);
                  });
                } else {
                  neighborMarkers.current.forEach((neighborMarker) => {
                    neighborMarker.setMap(mapRef.current);
                  });
                  helpMarkers.current.forEach((helpMarker) => {
                    helpMarker.setMap(mapRef.current);
                  });
                }
              }
            });
            naver.maps.Event.addDOMListener(
              centerBtnIndicator_inactive.getElement(),
              "click",
              () => {
                centerBtnIndicator_inactive.setMap(null);
                centerBtnIndicator_active.setMap(map);
                centerBtnRef.current = centerBtnIndicator_active;
                setOnSearch(true);
              },
            );
            naver.maps.Event.addDOMListener(
              binBtnIndicator_inactive.getElement(),
              "click",
              () => {
                toiletBtnRef.current?.setMap(null);
                binBtnIndicator_inactive.setMap(null);
                binBtnIndicator_active.setMap(map);
                toiletBtnRef.current?.setMap(map);
                binBtnRef.current = binBtnIndicator_active;
                setShowBin(true);
              },
            );
            naver.maps.Event.addDOMListener(
              binBtnIndicator_active.getElement(),
              "click",
              () => {
                toiletBtnRef.current?.setMap(null);
                binBtnIndicator_active.setMap(null);
                binBtnIndicator_inactive.setMap(map);
                toiletBtnRef.current?.setMap(map);
                binBtnRef.current = binBtnIndicator_inactive;
                setShowBin(false);
              },
            );
            naver.maps.Event.addDOMListener(
              toiletBtnIndicator_inactive.getElement(),
              "click",
              () => {
                toiletBtnIndicator_inactive.setMap(null);
                toiletBtnIndicator_active.setMap(map);
                toiletBtnRef.current = toiletBtnIndicator_active;
                setShowToilet(true);
              },
            );
            naver.maps.Event.addDOMListener(
              toiletBtnIndicator_active.getElement(),
              "click",
              () => {
                toiletBtnIndicator_active.setMap(null);
                toiletBtnIndicator_inactive.setMap(map);
                toiletBtnRef.current = toiletBtnIndicator_inactive;
                setShowToilet(false);
              },
            );
          });
        })
        .catch((error) => {
          isFailed = true;
          console.error(error);
        });
    }

    function handleResize() {
      setWindowHeight(window.innerHeight);
    }
    window.addEventListener("resize", handleResize);

    return () => {
      if (preventDup.current) {
        if (isFailed) {
          alert(`GPS 정보를 불러오는데 실패했습니다.`);
          // 다른 페이지로 이동 등의 로직 설정
        }
        preventDup.current = false;
      }

      window.removeEventListener("resize", handleResize);
    };
  }, []);

  useEffect(() => {
    if (!preventDup.current && !onSearch) {
      if (typeof latitude === "number" && typeof longitude === "number") {
        mapRef.current?.setCenter(new naver.maps.LatLng(latitude, longitude));
        userRef.current?.setPosition(
          new naver.maps.LatLng(latitude, longitude),
        );
      }
    }
  }, [onSearch]);

  // 쓰레기통 및 화장실 데이터 로드
  // 향후 도움 요청은 어떻게 넣을지에 대해서 고민할 것
  useEffect(() => {
    if (!preventDup.current) {
      const { makeMarkerClustering_blue, makeMarkerClustering_green } =
        useCluster();

      binMarkers.current = [];
      bins.forEach((bin) => {
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(bin.latitude, bin.longitude),
          map: undefined,
          icon: {
            url: `images/PloggingPage/bin-icon.png`,
            size: new naver.maps.Size(35, 35),
            scaledSize: new naver.maps.Size(35, 35),
            origin: new naver.maps.Point(0, 0),
            anchor: new naver.maps.Point(17.5, 17.5),
          },
        });
        binMarkers.current = [...binMarkers.current, marker];
      });

      binCluster.current = makeMarkerClustering_green({
        map: undefined,
        markers: binMarkers.current,
      });

      toiletMarkers.current = [];
      toilets.forEach((toilet) => {
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(toilet.latitude, toilet.longitude),
          map: undefined,
          icon: {
            url: `images/PloggingPage/toilet-icon.png`,
            size: new naver.maps.Size(35, 35),
            scaledSize: new naver.maps.Size(35, 35),
            origin: new naver.maps.Point(0, 0),
            anchor: new naver.maps.Point(17.5, 17.5),
          },
        });

        toiletMarkers.current = [...toiletMarkers.current, marker];
      });

      toiletCluster.current = makeMarkerClustering_blue({
        map: undefined,
        markers: toiletMarkers.current,
      });
    }
  }, [bins, toilets]);

  // 쓰레기통과 화장실 visibility를 toggle
  useEffect(() => {
    if (!preventDup.current) {
      if (showBin) {
        binMarkers.current.forEach((binMarker) => {
          binMarker.setMap(mapRef.current);
        });
        binCluster.current.setMap(mapRef.current);
      } else {
        binMarkers.current.forEach((binMarker) => {
          binMarker.setMap(null);
        });
        binCluster.current.setMap(null);
      }

      if (showToilet) {
        toiletMarkers.current.forEach((toiletMarker) => {
          toiletMarker.setMap(mapRef.current);
        });
        toiletCluster.current.setMap(mapRef.current);
      } else {
        toiletMarkers.current.forEach((toiletMarker) => {
          toiletMarker.setMap(null);
        });
        toiletCluster.current.setMap(null);
      }
    }
  }, [showBin, showToilet]);

  useEffect(() => {
    if (!preventDup.current) {
      neighborMarkers.current = [];
      neighbors.forEach((neighbor) => {
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(
            neighbor.latitude,
            neighbor.longitude,
          ),
          map: undefined,
          icon: {
            url: `images/PloggingPage/neighborLocation.svg`,
            size: new naver.maps.Size(35, 35),
            scaledSize: new naver.maps.Size(35, 35),
            origin: new naver.maps.Point(0, 0),
            anchor: new naver.maps.Point(17.5, 17.5),
          },
          cursor: "default",
        });
        neighborMarkers.current = [...neighborMarkers.current, marker];
      });
    }
  }, [neighbors]);

  useEffect(() => {
    if (!preventDup.current) {
      helpMarkers.current = [];
      helps.forEach((help) => {
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(help.latitude, help.longitude),
          map: undefined,
          icon: {
            url: `images/PloggingPage/help-icon.png`,
            size: new naver.maps.Size(35, 35),
            scaledSize: new naver.maps.Size(35, 35),
            origin: new naver.maps.Point(0, 0),
            anchor: new naver.maps.Point(17.5, 17.5),
          },
        });

        naver.maps.Event.addListener(marker, "click", () => {
          Swal.fire({
            imageUrl: help.image,
            imageWidth: `70vw`,
            imageHeight: `auto`,
            imageAlt: `helpImage`,
            text: help.context,
            showConfirmButton: false,
            showCloseButton: true,
          });
        });
        helpMarkers.current = [...helpMarkers.current, marker];
      });
    }
  }, [helps]);

  return (
    <div
      style={{
        height: `${windowHeight - subHeight}px`,
        width: "100%",
        transition: `all 200ms ease-in-out`,
      }}
    >
      <div id="map" style={{ height: "100%", width: "100%" }}></div>
    </div>
  );
};

export default DefaultMap;
