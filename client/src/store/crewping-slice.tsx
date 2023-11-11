import { PayloadAction, createSlice } from "@reduxjs/toolkit";

interface Location {
  latitude: number;
  longitude: number;
}

interface Locations {
  [key: string]: Location;
}

interface UserImages {
  [key: string]: string;
}

const initialState = {
  charge: false as boolean,
  isLoading: false as boolean,
  startCrewping: false as boolean,
  endCrewping: false as boolean,
  roomId: "" as string,
  senderId: "" as string,
  locations: {} as Locations,
  userImages: {} as UserImages,
};

const crewpingSlice = createSlice({
  name: "crewping",
  initialState,
  reducers: {
    clear: () => {
      return initialState;
    },
    setCharge: (state, action: PayloadAction<boolean>) => {
      state.charge = action.payload;
    },
    setIsLoading: (state, action: PayloadAction<boolean>) => {
      state.isLoading = action.payload;
    },
    setStartCrewping: (state, action: PayloadAction<boolean>) => {
      state.startCrewping = action.payload;
    },
    setEndCrewping: (state, action: PayloadAction<boolean>) => {
      state.endCrewping = action.payload;
    },
    setRoomId: (state, action: PayloadAction<string>) => {
      state.roomId = action.payload;
    },
    setSenderId: (state, action: PayloadAction<string>) => {
      state.senderId = action.payload;
    },
    setLocations: (state, action: PayloadAction<Locations>) => {
      state.locations = action.payload;
    },
    setUserImages: (state, action: PayloadAction<UserImages>) => {
      state.userImages = action.payload;
    },
  },
});

export const {
  clear,
  setCharge,
  setIsLoading,
  setStartCrewping,
  setEndCrewping,
  setRoomId,
  setSenderId,
  setLocations,
  setUserImages,
} = crewpingSlice.actions;
export default crewpingSlice.reducer;
