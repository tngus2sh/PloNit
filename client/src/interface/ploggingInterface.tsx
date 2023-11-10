import React from "react";

export interface GeolocationPosition {
  coords: GeolocationCoordinates;
  timestamp: number;
}

export interface Coordinate {
  latitude: number;
  longitude: number;
  place?: string;
  image?: string;
  context?: string;
}

export interface PloggingLog {
  id: number;
  type: string;
  place: string;
  start_time?: string;
  end_time?: string;
  total_time?: string;
  distance?: string;
}
