import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  isLogin: false,
  accessToken: "",
  refreshToken: "",
  profileImg: "",
  email: "",
  nickname: "",
  name: "",
  gender: 0,
  birthday: "",
  region: "",
  height: 0,
  weight: 0,
  id_1365: "",
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    login: (state, action) => {
      state.isLogin = true;
      state.accessToken = action.payload.accessToken;
      state.refreshToken = action.payload.refreshToken;
    },
    logout: (state) => {
      state.isLogin = false;
    },
  },
});

export const { login, logout } = userSlice.actions;
export default userSlice.reducer;
