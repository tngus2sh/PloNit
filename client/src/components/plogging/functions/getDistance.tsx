import { getDistance as getDist } from "geolib";
import { GeolibInputCoordinates } from "geolib/es/types";

function getDistance(
  start: GeolibInputCoordinates,
  end: GeolibInputCoordinates,
): number {
  return getDist(start, end) / 1000;
}

export default getDistance;
