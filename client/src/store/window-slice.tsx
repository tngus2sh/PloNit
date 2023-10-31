import { PayloadAction, createSlice } from "@reduxjs/toolkit";

const initialState = {
  height: window.innerHeight as number,
  width: window.innerWidth as number,
};

const windowSlice = createSlice({
  name: "window",
  initialState: initialState,
  reducers: {
    setWindowHeight: (state, action: PayloadAction<number>) => {
      state.height = action.payload;
    },
    setWindowWidth: (state, action: PayloadAction<number>) => {
      state.width = action.payload;
    },
  },
});

export const { setWindowHeight, setWindowWidth } = windowSlice.actions;
export default windowSlice.reducer;
