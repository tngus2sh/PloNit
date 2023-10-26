import { Coordinate, Help } from "interface/ploggingInterface";

const dummy_location: Coordinate[] = [
  { latitude: 35.147824, longitude: 126.920026 },
  { latitude: 35.2059392, longitude: 126.81216 },
  { latitude: 35.160173, longitude: 126.851935 },
  { latitude: 35.148866, longitude: 126.926669 },
  { latitude: 35.154255, longitude: 126.801707 },
  { latitude: 35.156394, longitude: 126.849242 },
  { latitude: 35.150342, longitude: 126.867053 },
];

const dummy_helps: Help[] = [
  {
    latitude: 35.17421,
    longitude: 126.912198,
    image: "./crewbg.png",
    context: "북구청 더러워요.",
  },
];

export { dummy_location, dummy_helps };
