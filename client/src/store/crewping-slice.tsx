import { PayloadAction, createSlice } from "@reduxjs/toolkit";

interface Location {
  latitude: number;
  longitude: number;
}

interface Locations {
  [key: string]: Location;
}

const initialState = {
  peers: {} as Locations,
};

const crewpingSlice = createSlice({
  name: "crewping",
  initialState,
  reducers: {
    clear: () => {
      return initialState;
    },
    setPeers: (state, action: PayloadAction<Locations>) => {
      state.peers = action.payload;
    },
  },
});

export const { clear, setPeers } = crewpingSlice.actions;
export default crewpingSlice.reducer;
