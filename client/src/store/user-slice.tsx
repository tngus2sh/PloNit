import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  isLogin: false,
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    login: (state) => {
      state.isLogin = true;
    },
    logout: (state) => {
      state.isLogin = false;
    },
  },
});

export const { login, logout } = userSlice.actions;
export default userSlice.reducer;
