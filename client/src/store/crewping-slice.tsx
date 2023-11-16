import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { Locations, Message, Member } from "interface/ploggingInterface";

const initialState = {
  crewId: -1 as number,
  roomId: "" as string,
  senderId: "" as string,
  charge: false as boolean,
  isLoading: false as boolean,
  startRequest: false as boolean,
  crewpingStart: false as boolean,
  endRequest: false as boolean,
  crewpingEnd: false as boolean,
  exitRequest: false as boolean,
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
    setCrewId: (state, action: PayloadAction<number>) => {
      state.crewId = action.payload;
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
    setExitRequest: (state) => {
      state.exitRequest = true;
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
        const { nickName, profileImage, members } = newMessage;
        const includeMyInfo = members.some((member) => {
          return member.nickName === state.senderId;
        });

        let data: Member[] = members;
        if (!includeMyInfo) {
          data = [
            ...data,
            { nickName: state.senderId, profileImage: state.profileImage },
          ];
        }
        if (
          !data.some((item) => {
            return item.nickName === nickName;
          })
        ) {
          if (profileImage)
            data = [
              ...data,
              {
                nickName: nickName,
                profileImage: profileImage,
              },
            ];
        }

        state.members = data;
      }
    },
  },
});

export const {
  clear,
  setCrewId,
  setRoomId,
  setSenderId,
  setCharge,
  setIsLoading,
  setStartRequest,
  setCrewpingStart,
  setEndRequest,
  setCrewpingEnd,
  setExitRequest,
  setGetLocation,
  setLocations,
  setProfileImage,
  setMembers,
} = crewpingSlice.actions;
export default crewpingSlice.reducer;
