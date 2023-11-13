import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import {
  Location,
  Locations,
  UserImages,
  Message,
} from "interface/ploggingInterface";

const initialState = {
  roomId: "" as string,
  senderId: "" as string,
  charge: false as boolean,
  isLoading: false as boolean,
  startRequest: false as boolean,
  crewpingStart: false as boolean,
  endRequest: false as boolean,
  crewpingEnd: false as boolean,
  getLocation: false as boolean,
  locations: {} as Locations,
  userImages: {
    박주성:
      "https://post-phinf.pstatic.net/MjAyMDExMDRfODgg/MDAxNjA0NDUyNDkwNTk5.3qW-hU8R0DvdQW8bgDuldGPN27uFdDvTh7haVQTpDqgg.2Tj5OcLz-xJx4rAFgkN48q8w5hrN5QahTK_DDIsNo2Ig.PNG/VneKfm5.png?type=w800_q75",
  } as UserImages,
};

const crewpingSlice = createSlice({
  name: "crewping",
  initialState,
  reducers: {
    clear: () => {
      return initialState;
    },
    setRoomId: (state, action: PayloadAction<string>) => {
      state.roomId = action.payload;
    },
    setSenderId: (state, action: PayloadAction<string>) => {
      state.senderId = action.payload;
    },
    setCharge: (state, action: PayloadAction<boolean>) => {
      state.charge = action.payload;
    },
    setIsLoading: (state, action: PayloadAction<boolean>) => {
      state.isLoading = action.payload;
    },
    setStartRequest: (state, action: PayloadAction<boolean>) => {
      state.startRequest = action.payload;
    },
    setCrewpingStart: (state, action: PayloadAction<boolean>) => {
      state.crewpingStart = action.payload;
    },
    setEndRequest: (state, action: PayloadAction<boolean>) => {
      state.endRequest = action.payload;
    },
    setCrewpingEnd: (state, action: PayloadAction<boolean>) => {
      state.crewpingEnd = action.payload;
    },
    setGetLocation: (state, action: PayloadAction<boolean>) => {
      state.getLocation = action.payload;
    },
    setLocations: (state, action: PayloadAction<Message>) => {
      const newMessage = action.payload;
      if (newMessage.location) {
        state.locations = {
          ...state.locations,
          [newMessage.senderId]: newMessage.location,
        };
      }
    },
    addUserImage: (
      state,
      action: PayloadAction<{ senderId: string; image: string }>,
    ) => {
      const { senderId, image } = action.payload;
      state.userImages = { ...state.userImages, [senderId]: image };
    },
    setUserImages: (state, action: PayloadAction<Message>) => {
      const newMessage = action.payload;
      if (newMessage.userImages) {
        state.userImages = newMessage.userImages;
      }
    },
  },
});

export const {
  clear,
  setRoomId,
  setSenderId,
  setCharge,
  setIsLoading,
  setStartRequest,
  setCrewpingStart,
  setEndRequest,
  setCrewpingEnd,
  setGetLocation,
  setLocations,
  addUserImage,
  setUserImages,
} = crewpingSlice.actions;
export default crewpingSlice.reducer;
