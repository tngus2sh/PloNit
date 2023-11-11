import { createSlice } from "@reduxjs/toolkit";
import { act } from "react-dom/test-utils";

const initialState = {
  auth: {
    isLogin: false,
    accessToken: "",
    refreshToken: "",
  },
  info: {
    profileImg: "",
    email: "",
    nickname: "",
    name: "",
    gender: false,
    birthday: "",
    dongCode: 0,
    region: "",
    height: 0,
    weight: 0,
    id1365: "",
  },
  crewinfo: {
    isMyCrew: false,
    isCrewMaster: false,
    isCrewwpingMaster: false,
  },
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    loginHandler: (state, action) => {
      state.auth.isLogin = true;
      state.auth.accessToken = action.payload.accessToken;
      state.auth.refreshToken = action.payload.refreshToken;
    },
    logout: (state) => {
      localStorage.removeItem("persist:PloNit");
    },
    saveMemberInfo(state, action) {
      return { ...state, info: action.payload };
    },
    myCrewHandler(state, action) {
      state.crewinfo.isMyCrew = action.payload.isMyCrew;
      state.crewinfo.isCrewMaster = action.payload.isCrewMaster;
      state.crewinfo.isCrewwpingMaster = action.payload.isCrewwpingMaster;
    },
  },
});

// export const { loginHandler, logout } = userSlice.actions;
export const userActions = userSlice.actions;
export default userSlice.reducer;
