import { GeolocationPosition } from "interface/ploggingInterface";

async function getGPS(): Promise<GeolocationPosition> {
  return new Promise((resolve, reject) => {
    navigator.geolocation.getCurrentPosition(resolve, reject);
  });
}

export default getGPS;
