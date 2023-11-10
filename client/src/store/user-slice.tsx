import { createSlice } from "@reduxjs/toolkit";
import { act } from "react-dom/test-utils";

const initialState = {
  isLogin: false,
  accessToken: "",
  refreshToken: "",
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
  id_1365: "",
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    loginHandler: (state, action) => {
      state.isLogin = true;
      state.accessToken = action.payload.accessToken;
      state.refreshToken = action.payload.refreshToken;
      // state.profileImg = action.payload.profileImg;
      state.nickname = action.payload.nickname;
    },
    logout: (state) => {
      localStorage.removeItem("persist:PloNit");
    },
    addInfoHandler: (state, action) => {
      return {
        ...state,
        name: action.payload.name,
        nickname: action.payload.nickname,
        gender: action.payload.gender,
        birthday: action.payload.birth,
        dongCode: action.payload.dongCode,
        region: action.payload.region,
      };
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
