import React from "react";

export interface GeolocationPosition {
  coords: GeolocationCoordinates;
  timestamp: number;
}

export interface Coordinate {
  latitude: number;
  longitude: number;
}

export interface Help {
  latitude: number;
  longitude: number;
  image: string;
  context: string;
}
