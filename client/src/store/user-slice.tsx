import { createSlice } from "@reduxjs/toolkit";
import { act } from "react-dom/test-utils";

const initialState = {
  auth: {
    isLogin: false,
    accessToken: "",
    refreshToken: "",
  },
  info: {
    profileImage: "",
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
    isCrewpingMaster: "",
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
      // localStorage.removeItem("persist:PloNit");
      localStorage.clear();
    },
    saveMemberInfo(state, action) {
      return { ...state, info: action.payload };
    },
    myCrewHandler(state, action) {
      return { ...state, crewinfo: action.payload };
    },
  },
});

// export const { loginHandler, logout } = userSlice.actions;
export const userActions = userSlice.actions;
export default userSlice.reducer;
