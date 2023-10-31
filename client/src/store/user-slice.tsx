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
    loginHandler: (state, action) => {
      state.isLogin = true;
      state.accessToken = action.payload.accessToken;
      state.refreshToken = action.payload.refreshToken;
    },
    logout: (state) => {
      state.isLogin = false;
    },
  },
});

// export const { loginHandler, logout } = userSlice.actions;
export const userActions = userSlice.actions;
export default userSlice.reducer;
