import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { Coordinate } from "interface/ploggingInterface";
import getDistance from "components/plogging/functions/getDistance";
import getCalorie from "components/plogging/functions/getCalorie";
import { ploggingType } from "types/ploggingTypes";

const initialState = {
  isStart: true as boolean,
  isEnd: false as boolean,
  beforeEnd: false as boolean,
  beforeCrewping: false as boolean,
  ploggingType: "none" as ploggingType,
  ploggingId: -1 as number,
  paths: [] as Coordinate[],
  pathlen: 0 as number,
  second: 0 as number,
  minute: 0 as number,
  distance: 0 as number,
  calorie: 0 as number,
  images: [] as string[],
  isVolStart: false as boolean,
  volTakePicture: false as boolean,
  isVolTakePicture: false as boolean,
  isVolEnd: false as boolean,
  kg: 65 as number,
};

const intervalTime = 10;

const ploggingSlice = createSlice({
  name: "plogging",
  initialState,
  reducers: {
    clear: () => {
      return initialState;
    },
    setIsStart: (state, action: PayloadAction<boolean>) => {
      state.isStart = action.payload;
    },
    setIsEnd: (state, action: PayloadAction<boolean>) => {
      state.isEnd = action.payload;
    },
    setBeforeEnd: (state, action: PayloadAction<boolean>) => {
      state.beforeEnd = action.payload;
    },
    setBeforeCrewping: (state, action: PayloadAction<boolean>) => {
      state.beforeCrewping = action.payload;
    },
    setPloggingType: (state, action: PayloadAction<ploggingType>) => {
      state.ploggingType = action.payload;
    },
    setPloggingId: (state, action: PayloadAction<number>) => {
      state.ploggingId = action.payload;
    },
    addPath: (state, action: PayloadAction<Coordinate>) => {
      state.paths.push(action.payload);

      if (++state.pathlen > 1) {
        const distance = getDistance(
          state.paths[state.pathlen - 2],
          state.paths[state.pathlen - 1],
        );
        state.distance += distance;
        state.calorie += getCalorie({
          second: intervalTime,
          distance: distance,
          kg: state.kg,
        });
      }
    },
    addImage: (state, action: PayloadAction<string>) => {
      state.images.push(action.payload);
    },
    addTime: (state) => {
      if (++state.second === 60) {
        state.second = 0;
        state.minute++;
      }
    },
    setIsVolStart: (state, action: PayloadAction<boolean>) => {
      state.isVolStart = action.payload;
    },
    setVolTakePicture: (state, action: PayloadAction<boolean>) => {
      state.volTakePicture = action.payload;
    },
    setIsVolTakePicture: (state, action: PayloadAction<boolean>) => {
      state.isVolTakePicture = action.payload;
    },
    setIsVolEnd: (state, action: PayloadAction<boolean>) => {
      state.isVolEnd = action.payload;
    },
    setKg: (state, action: PayloadAction<number>) => {
      state.kg = action.payload;
    },
  },
});

export const {
  clear,
  setIsStart,
  setIsEnd,
  setBeforeEnd,
  setBeforeCrewping,
  setPloggingType,
  setPloggingId,
  addPath,
  addImage,
  addTime,
  setIsVolStart,
  setVolTakePicture,
  setIsVolTakePicture,
  setIsVolEnd,
  setKg,
} = ploggingSlice.actions;
export default ploggingSlice.reducer;
