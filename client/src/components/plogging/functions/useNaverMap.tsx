import { naver } from "components/common/useNaver";
import { NavigateProps } from "react-router-dom";

interface createMarker {
  latitude: number;
  longitude: number;
  map: naver.maps.Map | undefined;
  url: string;
  cursor?: string;
}

interface createCustomControl {
  html: string;
  pos: naver.maps.Position;
}

interface controlMarkers {
  markers: naver.maps.Marker;
  map: naver.maps.Map | null;
}

function createMarker({
  latitude,
  longitude,
  map,
  url,
  cursor = "pointer",
}: createMarker): naver.maps.Marker {
  const marker = new naver.maps.Marker({
    position: new naver.maps.LatLng(latitude, longitude),
    map: map,
    icon: {
      url: url,
      size: new naver.maps.Size(35, 35),
      scaledSize: new naver.maps.Size(35, 35),
      origin: new naver.maps.Point(0, 0),
      anchor: new naver.maps.Point(17.5, 17.5),
    },
    cursor: cursor,
  });

  return marker;
}

function createCustomControl({
  html,
  pos,
}: createCustomControl): naver.maps.CustomControl {
  const CustomControl = new naver.maps.CustomControl(html, {
    position: pos,
  });

  return CustomControl;
}

function attachMarkers({ markers, map }: controlMarkers) {
  console.log(``);
}

function detachMarkers({ markers, map }: controlMarkers) {
  console.log(``);
}
