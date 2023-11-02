import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { Coordinate } from "interface/ploggingInterface";
import getDistance from "components/plogging/functions/getDistance";
import { ploggingType } from "types/ploggingTypes";

const initialState = {
  isStart: true as boolean,
  ploggingType: "none" as ploggingType,
  crewpingId: -1 as number,
  paths: [] as Coordinate[],
  pathlen: 0 as number,
  second: 0 as number,
  minute: 0 as number,
  distance: 0 as number,
  calorie: 0 as number,
  images: [] as string[],
  cbURL: "/" as string,
};

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
    setPloggingType: (state, action: PayloadAction<ploggingType>) => {
      state.ploggingType = action.payload;
    },
    setCrewpingId: (state, action: PayloadAction<number>) => {
      state.crewpingId = action.payload;
    },
    addPath: (state, action: PayloadAction<Coordinate>) => {
      state.paths.push(action.payload);

      if (++state.pathlen > 1) {
        state.distance += getDistance(
          state.paths[state.pathlen - 2],
          state.paths[state.pathlen - 1],
        );
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
    setCbURL: (state, action: PayloadAction<string>) => {
      state.cbURL = action.payload;
    },
  },
});

export const {
  clear,
  setIsStart,
  setPloggingType,
  setCrewpingId,
  addPath,
  addImage,
  addTime,
  setCbURL,
} = ploggingSlice.actions;
export default ploggingSlice.reducer;
