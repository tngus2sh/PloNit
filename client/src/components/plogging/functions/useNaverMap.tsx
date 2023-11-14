import React from "react";
import { naver } from "components/common/useNaver";
import { Coordinate } from "interface/ploggingInterface";
import Swal from "sweetalert2";
import { renderToString } from "react-dom/server";

interface IcreateMarker {
  latlng: naver.maps.Coord | naver.maps.CoordLiteral;
  map: naver.maps.Map | undefined;
  url: string;
  cursor?: string;
  zIndex?: number;
  draggable?: boolean;
}

interface IcreateCustomControl {
  html: string;
  pos: naver.maps.Position;
}

interface IcreateMarkers {
  items: Coordinate[];
  map: naver.maps.Map | undefined;
  url: string;
  cursor?: string;
  draggable?: boolean;
}

interface IcontrolMarkers {
  markers: naver.maps.Marker[];
  map: naver.maps.Map | null;
}

interface IcreatePolyline {
  map: naver.maps.Map | undefined;
  path: naver.maps.Coord[] | naver.maps.CoordLiteral[];
  style?: {
    strokeColor?: string;
    strokeOpacity?: number;
    strokeWeight?: number;
    strokeStyle?: naver.maps.StrokeStyleType;
    zIndex?: number;
    clickable?: boolean;
  };
}

function createMarker({
  latlng,
  map,
  url,
  cursor = "pointer",
  zIndex,
  draggable,
}: IcreateMarker): naver.maps.Marker {
  const marker = new naver.maps.Marker({
    position: latlng,
    map: map,
    icon: {
      url: url,
      size: new naver.maps.Size(35, 35),
      scaledSize: new naver.maps.Size(35, 35),
      origin: new naver.maps.Point(0, 0),
      anchor: new naver.maps.Point(17.5, 17.5),
    },
    cursor: cursor,
    zIndex: zIndex,
    draggable: draggable,
  });

  return marker;
}

function createMarker_small({
  latlng,
  map,
  url,
  cursor = "pointer",
  draggable,
}: IcreateMarker): naver.maps.Marker {
  const marker = new naver.maps.Marker({
    position: latlng,
    map: map,
    icon: {
      url: url,
      size: new naver.maps.Size(20, 20),
      scaledSize: new naver.maps.Size(20, 20),
      origin: new naver.maps.Point(0, 0),
      anchor: new naver.maps.Point(10, 10),
    },
    cursor: cursor,
    draggable: draggable,
  });

  return marker;
}

function createCustomControl({
  html,
  pos,
}: IcreateCustomControl): naver.maps.CustomControl {
  const CustomControl = new naver.maps.CustomControl(html, {
    position: pos,
  });

  return CustomControl;
}

function createMarkers({
  items,
  map,
  url,
  cursor = "pointer",
  draggable,
}: IcreateMarkers): naver.maps.Marker[] {
  const markers: naver.maps.Marker[] = [];
  items.forEach((item) => {
    const marker = new naver.maps.Marker({
      position: new naver.maps.LatLng(item.latitude, item.longitude),
      map: map,
      icon: {
        url: url,
        size: new naver.maps.Size(35, 35),
        scaledSize: new naver.maps.Size(35, 35),
        origin: new naver.maps.Point(0, 0),
        anchor: new naver.maps.Point(17.5, 17.5),
      },
      cursor: cursor,
      draggable: draggable,
    });
    markers.push(marker);
  });

  const InnerComponent = ({
    place,
    context,
  }: {
    place: string;
    context: string;
  }) => {
    return (
      <div>
        <div
          style={{
            fontSize: "1.5rem",
            fontWeight: "bolder",
            lineHeight: "1.5rem",
          }}
        >
          <img
            src="/images/PloggingPage/nowLocation-pin.png"
            style={{
              height: "1.5rem",
              aspectRatio: "1/1",
              verticalAlign: "middle",
            }}
          />
          {place}
        </div>
        <br />
        <div>{context}</div>
      </div>
    );
  };

  // Help의 경우
  if (items[0]?.id || items[0]?.image || items[0]?.place || items[0]?.context) {
    markers.forEach((marker, index) => {
      naver.maps.Event.addListener(marker, "click", () => {
        Swal.fire({
          imageUrl: items[index].image,
          imageWidth: `min(70vw, 400px)`,
          imageHeight: `auto`,
          imageAlt: `helpImage`,
          html: renderToString(
            InnerComponent({
              place: items[index].place ?? "",
              context: items[index].context ?? "",
            }),
          ),
          showConfirmButton: true,
          confirmButtonText: "도움요청 종료",
          showCloseButton: true,
        }).then((result) => {
          if (result.isConfirmed) {
            console.log("여기에 새로운 api가 들어갑니다.");
          }
        });
      });
    });
  }

  return markers;
}

function controlMarkers({ markers, map }: IcontrolMarkers): void {
  markers.forEach((marker) => {
    marker.setMap(map);
  });
}

function createPolyline({
  map,
  path,
  style,
}: IcreatePolyline): naver.maps.Polyline {
  const polyline = new naver.maps.Polyline({
    map: map,
    path: path,
    strokeColor: style?.strokeColor,
    strokeOpacity: style?.strokeOpacity,
    strokeWeight: style?.strokeWeight,
    strokeStyle: style?.strokeStyle,
    zIndex: style?.zIndex,
    clickable: style?.clickable,
  });

  return polyline;
}

export {
  createMarker,
  createMarker_small,
  createCustomControl,
  createMarkers,
  controlMarkers,
  createPolyline,
};
