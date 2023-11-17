import { PayloadAction, createSlice } from "@reduxjs/toolkit";

type targetType = "help" | "save";

const initialState = {
  value: "" as string,
  target: "save" as targetType,
  helpContext: "" as string,
  isOnWrite: false as boolean,
};

const cameraSlice = createSlice({
  name: "camera",
  initialState,
  reducers: {
    clear: () => {
      return initialState;
    },
    setTarget: (state, action: PayloadAction<targetType>) => {
      state.target = action.payload;
    },
    setImage: (state, action: PayloadAction<string>) => {
      state.value = action.payload;
    },
    setHelpContext: (state, action: PayloadAction<string>) => {
      state.helpContext = action.payload;
    },
    setIsOnWrite: (state, action: PayloadAction<boolean>) => {
      state.isOnWrite = action.payload;
    },
  },
});

export const { clear, setTarget, setImage, setHelpContext, setIsOnWrite } =
  cameraSlice.actions;
export default cameraSlice.reducer;
