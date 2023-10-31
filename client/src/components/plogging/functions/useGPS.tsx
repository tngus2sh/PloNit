import React, { useState, useEffect, useRef } from "react";
import { GeolocationPosition } from "interface/ploggingInterface";

function useGPS() {
  let isChecked = false;
  const [latitude, setLatitude] = useState<number>(0);
  const [longitude, setLongitude] = useState<number>(0);
  const [onSearch, setOnSearch] = useState<boolean>(false);
  const [onCenter, setOnCenter] = useState<boolean>(false);

  function getGPS(): Promise<GeolocationPosition> {
    return new Promise((resolve, reject) => {
      navigator.geolocation.getCurrentPosition(resolve, reject);
    });
  }

  useEffect(() => {
    if (!isChecked) {
      getGPS()
        .then((response) => {
          const { latitude, longitude } = response.coords;
          setLatitude(latitude);
          setLongitude(longitude);
        })
        .catch((error) => {
          console.error(error);
        });
    }

    return () => {
      if (!isChecked) {
        isChecked = true;
      }
    };
  }, []);

  useEffect(() => {
    if (isChecked && (onSearch || onCenter)) {
      getGPS()
        .then((response) => {
          const { latitude, longitude } = response.coords;
          setLatitude(latitude);
          setLongitude(longitude);
        })
        .catch((error) => {
          console.error(error);
        });
    }

    return () => {
      setOnSearch(false);
      setOnCenter(false);
    };
  }, [onSearch, onCenter]);

  return { latitude, longitude, onSearch, setOnSearch, onCenter, setOnCenter };
}
export default useGPS;
