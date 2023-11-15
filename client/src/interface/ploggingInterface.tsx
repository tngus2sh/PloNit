import React from "react";

export interface GeolocationPosition {
  coords: GeolocationCoordinates;
  timestamp: number;
}

export interface Coordinate {
  latitude: number;
  longitude: number;
  ploggingHelpId?: number;
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
  distance?: number;
  calorie?: number;
  images?: any;
  review?: string;
  coordinates?: Coordinate[];
}

export interface Location {
  latitude: number;
  longitude: number;
}

export interface Locations {
  [key: string]: Location;
}

export interface Member {
  nickName: string;
  profileImage: string;
}

export interface Message {
  type: "START" | "END" | "LOCATION" | "WAIT";
  nickName: string;
  senderId: string;
  roomId: string;
  message?: string;
  profileImage?: string;
  latitude?: number;
  longitude?: number;
  members?: Member[];
}
