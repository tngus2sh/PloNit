import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { Locations, Message, Member } from "interface/ploggingInterface";

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
  profileImage: "" as string,
  members: [] as Member[],
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
      const { senderId, latitude, longitude } = newMessage;
      state.locations = {
        ...state.locations,
        [senderId]: { latitude: latitude ?? 0, longitude: longitude ?? 0 },
      };
    },
    setProfileImage: (state, action: PayloadAction<string>) => {
      state.profileImage = action.payload;
    },
    setMembers: (state, action: PayloadAction<Message>) => {
      const newMessage = action.payload;
      if (newMessage.members) {
        const { members } = newMessage;
        const includeMyInfo = members.some((member) => {
          return member.nickName === state.senderId;
        });

        if (includeMyInfo) {
          state.members = members;
        } else {
          state.members = [
            { nickName: state.senderId, profileImage: state.profileImage },
            ...members,
          ];
        }
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
  setProfileImage,
  setMembers,
} = crewpingSlice.actions;
export default crewpingSlice.reducer;
