import { PayloadAction, createSlice } from "@reduxjs/toolkit";

const initialState = {
  value: window.innerHeight as number,
};

const windowHeightSlice = createSlice({
  name: "windowHeight",
  initialState: initialState,
  reducers: {
    setWindowHeight: (state, action: PayloadAction<number>) => {
      state.value = action.payload;
    },
  },
});

export const { setWindowHeight } = windowHeightSlice.actions;
export default windowHeightSlice.reducer;
