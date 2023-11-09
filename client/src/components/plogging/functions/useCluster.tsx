import React from "react";
import style from "styles/css/PloggingPage/useCluster.module.css";
import { MarkerClustering } from "./MarkerClustering";

const { naver } = window;

interface htmlMarker {
  content: string;
  size: naver.maps.Size;
  anchor: naver.maps.Point;
}

interface makeMarkerClustering {
  map: naver.maps.Map | null | undefined;
  markers: naver.maps.Marker[];
}

const maxZoom = 15;

function useCluster() {
  function htmlMarker(className: string): htmlMarker {
    return {
      content: `<div class=${className}></div>`,
      size: new naver.maps.Size(40, 40),
      anchor: new naver.maps.Point(20, 20),
    };
  }

  const htmlMarker_blue1 = htmlMarker(style.htmlMarker_blue1);
  const htmlMarker_blue2 = htmlMarker(style.htmlMarker_blue2);
  const htmlMarker_blue3 = htmlMarker(style.htmlMarker_blue3);
  const htmlMarker_blue4 = htmlMarker(style.htmlMarker_blue4);
  const htmlMarker_blue5 = htmlMarker(style.htmlMarker_blue5);
  const htmlMarker_green1 = htmlMarker(style.htmlMarker_green1);
  const htmlMarker_green2 = htmlMarker(style.htmlMarker_green2);
  const htmlMarker_green3 = htmlMarker(style.htmlMarker_green3);
  const htmlMarker_green4 = htmlMarker(style.htmlMarker_green4);
  const htmlMarker_green5 = htmlMarker(style.htmlMarker_green5);

  function makeMarkerClustering_blue({ map, markers }: makeMarkerClustering) {
    return new MarkerClustering({
      minClusterSize: 2,
      maxZoom: maxZoom,
      map: map,
      markers: markers,
      disableClickZoom: false,
      icons: [
        htmlMarker_blue5,
        htmlMarker_blue4,
        htmlMarker_blue3,
        htmlMarker_blue2,
        htmlMarker_blue1,
      ],
      indexGenerator: [10, 100, 200, 500, 1000],
      stylingFunction: function (clusterMarker: any, count: number) {
        const firstChildDiv = clusterMarker
          .getElement()
          .querySelector("div:first-child");
        if (firstChildDiv) {
          firstChildDiv.textContent = count.toString();
        }
      },
    });
  }

  function makeMarkerClustering_green({ map, markers }: makeMarkerClustering) {
    return new MarkerClustering({
      minClusterSize: 2,
      maxZoom: maxZoom,
      map: map,
      markers: markers,
      disableClickZoom: false,
      icons: [
        htmlMarker_green5,
        htmlMarker_green4,
        htmlMarker_green3,
        htmlMarker_green2,
        htmlMarker_green1,
      ],
      indexGenerator: [10, 100, 200, 500, 1000],
      stylingFunction: function (clusterMarker: any, count: number) {
        const firstChildDiv = clusterMarker
          .getElement()
          .querySelector("div:first-child");
        if (firstChildDiv) {
          firstChildDiv.textContent = count.toString();
        }
      },
    });
  }

  return { makeMarkerClustering_blue, makeMarkerClustering_green };
}

export default useCluster;
