import React from "react";

export interface GeolocationPosition {
  coords: GeolocationCoordinates;
  timestamp: number;
}

export interface Coordinate {
  id?: number;
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
  startTime?: string;
  endTime?: string;
  totalTime?: string;
  distance?: string;
  calorie?: number;
  image?: string;
  latitude?: number;
  longitude?: number;
}

export interface Location {
  latitude: number;
  longitude: number;
}

export interface Locations {
  [key: string]: Location;
}

export interface UserImages {
  [key: string]: string;
}

export interface Message {
  type: string;
  senderId: string;
  location?: Location;
  userImages?: UserImages;
  roomId: string;
}
