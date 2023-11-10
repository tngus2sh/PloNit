import React, { useState, useEffect, useRef, ReactNode } from "react";
import style from "styles/css/PloggingPage/DefaultMap.module.css";
import { GeolocationPosition, Coordinate } from "interface/ploggingInterface";
import getGPS from "./functions/getGPS";
import useCluster from "./functions/useCluster";
import * as N from "./functions/useNaverMap";
import { naver } from "components/common/useNaver";

import { useDispatch, useSelector } from "react-redux";
import { rootState } from "store/store";
import * as P from "store/plogging-slice";

import { getBins, getToilets } from "api/lib/items";
import { searchHelpUsingLatLng } from "api/lib/plogging";

const defaultZoom = 16;
const neighbor_help_maxZoom = 12;
const navbarHeight = 56;
const popupTime = 1;

interface IDefaultMap {
  subHeight: number;
  isBefore: boolean;
  children?: ReactNode;
}

const DefaultMap: React.FC<IDefaultMap> = ({
  subHeight,
  isBefore,
  children,
}) => {
  const dispatch = useDispatch();
  const windowHeight = useSelector<rootState, number>((state) => {
    return state.window.height;
  });
  const isStart = useSelector<rootState, boolean>((state) => {
    return state.plogging.isStart;
  });
  const pathlen = useSelector<rootState, number>((state) => {
    return state.plogging.pathlen;
  });
  const paths = useSelector<rootState, Coordinate[]>((state) => {
    return state.plogging.paths;
  });
  const [onCenter, setOnCenter] = useState<boolean>(false);
  const isMapLoaded = useRef<boolean>(false);
  const isBottomLoaded = useRef<boolean>(false);
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

  const accessToken = useSelector<rootState, string>((state) => {
    return state.user.accessToken;
  });

  const centerBtn_inactive = `<div class="${style.btn_white_margin_inc}" style="background-image:url('/images/PloggingPage/location-cross-black.svg')"></div>`;
  const centerBtn_active = `<div class="${style.btn_white_margin_inc}" style="background-image:url('/images/PloggingPage/location-cross-blue.svg')"></div>`;
  const binBtn_inactive = isBefore
    ? `<div class="${style.btn_black_margin_inc}" style="background-image:url('/images/PloggingPage/bin.svg')"></div>`
    : `<div class="${style.btn_black}" style="background-image:url('/images/PloggingPage/bin.svg')"></div>`;
  const binBtn_active = isBefore
    ? `<div class="${style.btn_green_margin_inc}" style="background-image:url('/images/PloggingPage/bin.svg')"></div>`
    : `<div class="${style.btn_green}" style="background-image:url('/images/PloggingPage/bin.svg')"></div>`;
  const toiletBtn_inactive = `<div class="${style.btn_black}" style="background-image:url('/images/PloggingPage/toilet.svg')"></div>`;
  const toiletBtn_active = `<div class="${style.btn_blue}" style="background-image:url('/images/PloggingPage/toilet.svg')"></div>`;
  const arrow_up = `<div class="${style.btn_black_margin_inc}" style="background-image:url('/images/PloggingPage/arrow-up.svg')"></div>`;
  const arrow_down = `<div class="${style.btn_black_margin_inc}" style="background-image:url('/images/PloggingPage/arrow-down.svg')"></div>`;

  // 초기맵 생성 및 기초 구조 구성
  useEffect(() => {
    if (!isMapLoaded.current) {
      getGPS()
        .then((response) => {
          mapRef.current = null;
          const { latitude, longitude } = response.coords;
          const map = new naver.maps.Map("map", {
            center: new naver.maps.LatLng(latitude, longitude),
            zoom: defaultZoom,
          });
          mapRef.current = map;

          const user = N.createMarker({
            latlng: new naver.maps.LatLng(latitude, longitude),
            map: map,
            url: `/images/PloggingPage/myLocation.svg`,
            cursor: "default",
            zIndex: 1001,
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
            centerBtnRef.current = centerBtnIndicator_active;
            if (!isBefore) {
              arrowBtnIndcator_up.setMap(map);
              arrowBtnRef.current = arrowBtnIndcator_up;
            }
            binBtnIndicator_inactive.setMap(map);
            toiletBtnIndicator_inactive.setMap(map);
            binBtnRef.current = binBtnIndicator_inactive;
            toiletBtnRef.current = toiletBtnIndicator_inactive;

            naver.maps.Event.addListener(map, "dragend", () => {
              setOnCenter(false);
              centerBtnIndicator_active.setMap(null);
              centerBtnIndicator_inactive.setMap(map);
              centerBtnRef.current = centerBtnIndicator_inactive;
            });
            naver.maps.Event.addListener(map, "zoom_changed", () => {
              setOnCenter(false);
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
                setOnCenter(true);
                centerBtnIndicator_inactive.setMap(null);
                centerBtnIndicator_active.setMap(map);
                centerBtnRef.current = centerBtnIndicator_active;
              },
            );
            naver.maps.Event.addDOMListener(
              centerBtnIndicator_active.getElement(),
              "click",
              () => {
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
          });

          // 플로깅 시작 시 정보창 애니메이션
          setTimeout(() => {
            if (
              !isBottomLoaded.current &&
              !isBefore &&
              isStart &&
              arrowBtnRef.current
            ) {
              isBottomLoaded.current = true;
              arrowBtnRef.current.getElement().click();
              dispatch(P.setIsStart(false));
            }
          }, popupTime * 1000);

          return { latitude: latitude, longitude: longitude };
        })
        .then((response) => {
          const { latitude, longitude } = response;
          getBins({
            accessToken: accessToken,
            latitude: latitude,
            longitude: longitude,
            success: (response) => {
              console.log(response.data.resultBody);
              setBins(response.data.resultBody);
            },
            fail: (error) => {
              console.error(error);
            },
          });

          getToilets({
            accessToken: accessToken,
            latitude: latitude,
            longitude: longitude,
            success: (response) => {
              console.log(response.data.resultBody);
              setToilets(response.data.resultBody);
            },
            fail: (error) => {
              console.error(error);
            },
          });

          searchHelpUsingLatLng({
            accessToken: accessToken,
            latitude: latitude,
            longitude: longitude,
            success: (response) => {
              console.log(response.data.resultBody);
              setHelps(response.data.resultBody);
            },
            fail: (error) => {
              console.error(error);
            },
          });
        })
        .catch((error) => {
          console.error(error);
          alert(`GPS를 불러올 수 없는 환경입니다.`);
          // 페이지 이동 로직 등
        });
    }

    return () => {
      if (!isMapLoaded.current) {
        isMapLoaded.current = true;
      }
    };
  }, []);

  // 사용자 위치 이동 시
  useEffect(() => {
    if (!isBefore && pathlen > 0) {
      const { latitude, longitude } = paths[pathlen - 1];
      userRef.current?.setPosition(new naver.maps.LatLng(latitude, longitude));
    }
  }, [pathlen]);

  // 사용자의 위치 가운데로 갱신 시
  useEffect(() => {
    if (onCenter) {
      getGPS()
        .then((response) => {
          setOnCenter(false);
          const { latitude, longitude } = response.coords;
          mapRef.current?.setCenter(new naver.maps.LatLng(latitude, longitude));
          userRef.current?.setPosition(
            new naver.maps.LatLng(latitude, longitude),
          );
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [onCenter]);

  // 쓰레기통 데이터 로드
  useEffect(() => {
    const { makeMarkerClustering_green } = useCluster();
    N.controlMarkers({
      markers: binMarkers.current,
      map: null,
    });

    binMarkers.current = binMarkers.current = N.createMarkers({
      items: bins,
      map: undefined,
      url: `/images/PloggingPage/bin-pin.png`,
      cursor: "default",
    });
    binCluster.current = makeMarkerClustering_green({
      map: undefined,
      markers: binMarkers.current,
    });
  }, [bins]);

  // 화장실 데이터 로드
  useEffect(() => {
    const { makeMarkerClustering_blue } = useCluster();
    N.controlMarkers({
      markers: toiletMarkers.current,
      map: null,
    });

    toiletMarkers.current = N.createMarkers({
      items: toilets,
      map: undefined,
      url: `/images/PloggingPage/toilet-pin.png`,
      cursor: "default",
    });
    toiletCluster.current = makeMarkerClustering_blue({
      map: undefined,
      markers: toiletMarkers.current,
    });
  }, [toilets]);

  // 쓰레기통 visibility를 toggle
  useEffect(() => {
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
  }, [showBin]);

  // 화장실 visibility를 toggle
  useEffect(() => {
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
  }, [showToilet]);

  // 주변 유저 데이터 로드
  useEffect(() => {
    N.controlMarkers({
      markers: neighborMarkers.current,
      map: null,
    });

    neighborMarkers.current = N.createMarkers({
      items: neighbors,
      map: mapRef.current ?? undefined,
      url: `/images/PloggingPage/neighborLocation.svg`,
      cursor: "default",
    });
  }, [neighbors]);

  // 도움 요청 데이터 로드
  useEffect(() => {
    N.controlMarkers({
      markers: helpMarkers.current,
      map: null,
    });

    helpMarkers.current = N.createMarkers({
      items: helps,
      map: mapRef.current ?? undefined,
      url: `/images/PloggingPage/help-icon.png`,
    });
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
        transition: `all 300ms ease-in-out`,
      }}
    >
      <div id="map" style={{ height: "100%", width: "100%" }}></div>
      {showBottom ? children : null}
    </div>
  );
};

export default DefaultMap;
