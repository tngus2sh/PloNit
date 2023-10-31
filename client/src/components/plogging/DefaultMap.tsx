import React, { useState, useEffect, useRef, ReactNode } from "react";
import style from "styles/css/PloggingPage/DefaultMap.module.css";
import Swal from "sweetalert2";
import { GeolocationPosition, Coordinate } from "interface/ploggingInterface";
import useGPS from "./functions/useGPS";
import useCluster from "./functions/useCluster";
import * as N from "./functions/useNaverMap";
import { naver } from "components/common/useNaver";

import { useSelector } from "react-redux";
import { rootState } from "store/store";
import { dummy_location, dummy_helps } from "./dummyData";

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

const defaultZoom = 16;
const neighbor_help_maxZoom = 12;
const navbarHeight = 56;

interface IDefaultMap {
  subHeight: number;
  isBefore: boolean;
  children?: ReactNode;
}

// let preventDup.current = true;
const DefaultMap: React.FC<IDefaultMap> = ({
  subHeight,
  isBefore,
  children,
}) => {
  const preventDup = useRef<boolean>(true);
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const { latitude, longitude, onCenter, setOnCenter } = useGPS();
  const mapRef = useRef<naver.maps.Map | null>(null);
  const userRef = useRef<naver.maps.Marker | null>(null);
  const centerBtnRef = useRef<naver.maps.CustomControl | null>(null);
  const binBtnRef = useRef<naver.maps.CustomControl | null>(null);
  const toiletBtnRef = useRef<naver.maps.CustomControl | null>(null);
  const arrowBtnRef = useRef<naver.maps.CustomControl | null>(null);
  const [showBin, setShowBin] = useState<boolean>(false);
  const [showToilet, setShowToilet] = useState<boolean>(false);
  const [bins, setBins] = useState<Coordinate[]>([]);
  const [toilets, setToilets] = useState<Coordinate[]>([]);
  const [neighbors, setNeighbors] = useState<Coordinate[]>([]);
  const [helps, setHelps] = useState<Coordinate[]>([]);
  const binMarkers = useRef<naver.maps.Marker[]>([]);
  const toiletMarkers = useRef<naver.maps.Marker[]>([]);
  const neighborMarkers = useRef<naver.maps.Marker[]>([]);
  const helpMarkers = useRef<naver.maps.Marker[]>([]);
  const binCluster = useRef<any>(null);
  const toiletCluster = useRef<any>(null);
  const [showBottom, setShowBottom] = useState<boolean>(isBefore);

  const centerBtn_inactive = `<div class="${style.btn_white_margin_inc}" style="background-image:url('images/PloggingPage/location-cross-black.svg')"></div>`;
  const centerBtn_active = `<div class="${style.btn_white_margin_inc}" style="background-image:url('images/PloggingPage/location-cross-blue.svg')"></div>`;
  const binBtn_inactive = isBefore
    ? `<div class="${style.btn_black_margin_inc}" style="background-image:url('images/PloggingPage/bin.svg')"></div>`
    : `<div class="${style.btn_black}" style="background-image:url('images/PloggingPage/bin.svg')"></div>`;
  const binBtn_active = isBefore
    ? `<div class="${style.btn_green_margin_inc}" style="background-image:url('images/PloggingPage/bin.svg')"></div>`
    : `<div class="${style.btn_green}" style="background-image:url('images/PloggingPage/bin.svg')"></div>`;
  const toiletBtn_inactive = `<div class="${style.btn_black}" style="background-image:url('images/PloggingPage/toilet.svg')"></div>`;
  const toiletBtn_active = `<div class="${style.btn_blue}" style="background-image:url('images/PloggingPage/toilet.svg')"></div>`;
  const arrow_up = `<div class="${style.btn_black_margin_inc}" style="background-image:url('images/PloggingPage/arrow-up.svg')"></div>`;
  const arrow_down = `<div class="${style.btn_black_margin_inc}" style="background-image:url('images/PloggingPage/arrow-down.svg')"></div>`;

  // 초기맵 생성 및 기초 구조 구성
  useEffect(() => {
    if (preventDup.current) {
      const getGPS = new Promise<GeolocationPosition>((resolve, reject) => {
        navigator.geolocation.getCurrentPosition(resolve, reject);
      });

      getGPS
        .then((response) => {
          // setBins(dummy_location);
          // setToilets(dummy_location);
          // setNeighbors(dummy_location);
          setHelps(dummy_helps);
          const { latitude, longitude } = response.coords;
          const map = new naver.maps.Map("map", {
            center: new naver.maps.LatLng(latitude, longitude),
            zoom: defaultZoom,
          });
          mapRef.current = map;

          const user = N.createMarker({
            latlng: new naver.maps.LatLng(latitude, longitude),
            map: map,
            url: `images/PloggingPage/myLocation.svg`,
            cursor: "default",
          });
          userRef.current = user;

          const centerBtnIndicator_inactive = N.createCustomControl({
            html: centerBtn_inactive,
            pos: naver.maps.Position.LEFT_BOTTOM,
          });
          const centerBtnIndicator_active = N.createCustomControl({
            html: centerBtn_active,
            pos: naver.maps.Position.LEFT_BOTTOM,
          });
          const binBtnIndicator_inactive = N.createCustomControl({
            html: binBtn_inactive,
            pos: naver.maps.Position.RIGHT_BOTTOM,
          });
          const binBtnIndicator_active = N.createCustomControl({
            html: binBtn_active,
            pos: naver.maps.Position.RIGHT_BOTTOM,
          });
          const toiletBtnIndicator_inactive = N.createCustomControl({
            html: toiletBtn_inactive,
            pos: naver.maps.Position.RIGHT_BOTTOM,
          });
          const toiletBtnIndicator_active = N.createCustomControl({
            html: toiletBtn_active,
            pos: naver.maps.Position.RIGHT_BOTTOM,
          });
          const arrowBtnIndcator_up = N.createCustomControl({
            html: arrow_up,
            pos: naver.maps.Position.RIGHT_BOTTOM,
          });
          const arrowBtnIndcator_down = N.createCustomControl({
            html: arrow_down,
            pos: naver.maps.Position.RIGHT_BOTTOM,
          });

          naver.maps.Event.once(map, "init", () => {
            centerBtnIndicator_active.setMap(map);
            if (!isBefore) {
              arrowBtnIndcator_up.setMap(map);
              arrowBtnRef.current = arrowBtnIndcator_up;
            }
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

              if (isBefore) {
                const currentZoom = map.getZoom();
                if (currentZoom <= neighbor_help_maxZoom) {
                  N.controlMarkers({
                    markers: neighborMarkers.current,
                    map: null,
                  });
                  N.controlMarkers({
                    markers: helpMarkers.current,
                    map: null,
                  });
                } else {
                  N.controlMarkers({
                    markers: neighborMarkers.current,
                    map: mapRef.current,
                  });
                  N.controlMarkers({
                    markers: helpMarkers.current,
                    map: mapRef.current,
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
                setOnCenter(true);
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
            if (!isBefore) {
              naver.maps.Event.addDOMListener(
                arrowBtnIndcator_up.getElement(),
                "click",
                () => {
                  setShowBottom(true);
                  arrowBtnIndcator_up.setMap(null);
                  toiletBtnRef.current?.setMap(null);
                  binBtnRef.current?.setMap(null);
                  arrowBtnIndcator_down.setMap(mapRef.current);
                  binBtnRef.current?.setMap(mapRef.current);
                  toiletBtnRef.current?.setMap(mapRef.current);
                  arrowBtnRef.current = arrowBtnIndcator_down;
                },
              );
              naver.maps.Event.addDOMListener(
                arrowBtnIndcator_down.getElement(),
                "click",
                () => {
                  setShowBottom(false);
                  arrowBtnIndcator_down.setMap(null);
                  toiletBtnRef.current?.setMap(null);
                  binBtnRef.current?.setMap(null);
                  arrowBtnIndcator_up.setMap(mapRef.current);
                  binBtnRef.current?.setMap(mapRef.current);
                  toiletBtnRef.current?.setMap(mapRef.current);
                  arrowBtnRef.current = arrowBtnIndcator_up;
                },
              );
            }
          });
        })
        .catch((error) => {
          console.error(error);
          alert(`GPS를 불러올 수 없는 환경입니다.`);
          // 페이지 이동 로직 등
        });
    }

    return () => {
      if (preventDup.current) {
        preventDup.current = false;
      }
    };
  }, []);

  // 사용자의 위치 가운데로 갱신 시
  useEffect(() => {
    if (!preventDup.current && !onCenter) {
      if (typeof latitude === "number" && typeof longitude === "number") {
        mapRef.current?.setCenter(new naver.maps.LatLng(latitude, longitude));
        userRef.current?.setPosition(
          new naver.maps.LatLng(latitude, longitude),
        );
      }
    }
  }, [onCenter]);

  // 쓰레기통 데이터 로드
  useEffect(() => {
    if (!preventDup.current) {
      const { makeMarkerClustering_green } = useCluster();
      N.controlMarkers({
        markers: binMarkers.current,
        map: null,
      });

      binMarkers.current = binMarkers.current = N.createMarkers({
        items: bins,
        map: undefined,
        url: `images/PloggingPage/bin-pin.png`,
        cursor: "default",
      });
      binCluster.current = makeMarkerClustering_green({
        map: undefined,
        markers: binMarkers.current,
      });
    }
  }, [bins]);

  // 화장실 데이터 로드
  useEffect(() => {
    if (!preventDup.current) {
      const { makeMarkerClustering_blue } = useCluster();
      N.controlMarkers({
        markers: toiletMarkers.current,
        map: null,
      });

      toiletMarkers.current = N.createMarkers({
        items: toilets,
        map: undefined,
        url: `images/PloggingPage/toilet-pin.png`,
        cursor: "default",
      });
      toiletCluster.current = makeMarkerClustering_blue({
        map: undefined,
        markers: toiletMarkers.current,
      });
    }
  }, [toilets]);

  // 쓰레기통 visibility를 toggle
  useEffect(() => {
    if (!preventDup.current) {
      if (showBin) {
        N.controlMarkers({
          markers: binMarkers.current,
          map: mapRef.current,
        });
        binCluster.current.setMap(mapRef.current);
      } else {
        N.controlMarkers({
          markers: binMarkers.current,
          map: null,
        });
        binCluster.current.setMap(null);
      }
    }
  }, [showBin]);

  // 화장실 visibility를 toggle
  useEffect(() => {
    if (!preventDup.current) {
      if (showToilet) {
        N.controlMarkers({
          markers: toiletMarkers.current,
          map: mapRef.current,
        });
        toiletCluster.current.setMap(mapRef.current);
      } else {
        N.controlMarkers({
          markers: toiletMarkers.current,
          map: null,
        });
        toiletCluster.current.setMap(null);
      }
    }
  }, [showToilet]);

  // 주변 유저 데이터 로드
  useEffect(() => {
    if (!preventDup) {
      N.controlMarkers({
        markers: neighborMarkers.current,
        map: null,
      });

      neighborMarkers.current = N.createMarkers({
        items: neighbors,
        map: mapRef.current ?? undefined,
        url: `images/PloggingPage/neighborLocation.svg`,
        cursor: "default",
      });
    }
  }, [neighbors]);

  // 도움 요청 데이터 로드
  useEffect(() => {
    if (!preventDup.current) {
      N.controlMarkers({
        markers: helpMarkers.current,
        map: null,
      });

      helpMarkers.current = N.createMarkers({
        items: helps,
        map: mapRef.current ?? undefined,
        url: `images/PloggingPage/help-icon.png`,
      });
    }
  }, [helps]);

  return (
    <div
      style={{
        height: `${
          showBottom
            ? windowHeight - navbarHeight - subHeight
            : windowHeight - navbarHeight
        }px`,
        width: "100%",
        transition: `all 200ms ease-in-out`,
      }}
    >
      <div id="map" style={{ height: "100%", width: "100%" }}></div>
      {showBottom ? children : null}
    </div>
  );
};

export default DefaultMap;
