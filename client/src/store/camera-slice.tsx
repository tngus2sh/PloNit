import { PayloadAction, createSlice } from "@reduxjs/toolkit";

const initialState = {
  value: "" as string,
};

const cameraSlice = createSlice({
  name: "camera",
  initialState,
  reducers: {
    clear: () => {
      return initialState;
    },
    setImage: (state, action: PayloadAction<string>) => {
      state.value = action.payload;
    },
  },
});

export const { clear, setImage } = cameraSlice.actions;
export default cameraSlice.reducer;
