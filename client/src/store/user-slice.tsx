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
    EditHandler: (state, action) => {
      return {
        ...state,
        profileImg: action.payload.profileImg,
        nickname: action.payload.nickname,
        weight: action.payload.weight,
        dongCode: action.payload.dongCode,
        region: action.payload.region,
        id_1365: action.payload.id_1365,
      };
    },
  },
});

// export const { loginHandler, logout } = userSlice.actions;
export const userActions = userSlice.actions;
export default userSlice.reducer;
