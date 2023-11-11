import { combineReducers, configureStore } from "@reduxjs/toolkit";
import {
  persistReducer,
  FLUSH,
  REHYDRATE,
  PAUSE,
  PERSIST,
  PURGE,
  REGISTER,
} from "redux-persist";
import storage from "redux-persist/lib/storage";

import userSlice from "./user-slice";
import windowSlice from "./window-slice";
import ploggingSlice from "./plogging-slice";
import cameraSlice from "./camera-slice";
import crewpingSlice from "./crewping-slice";

const rootReducer = combineReducers({
  user: userSlice,
  window: windowSlice,
  plogging: ploggingSlice,
  camera: cameraSlice,
  crewping: crewpingSlice,
});
const persistConfig = {
  key: "PloNit",
  storage: storage,
  whitelist: ["user"],
};

const persistedReducer = persistReducer(persistConfig, rootReducer);
const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) => {
    return getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: [FLUSH, REHYDRATE, PAUSE, PERSIST, PURGE, REGISTER],
      },
    });
  },
});

export type rootState = ReturnType<typeof store.getState>;
export default store;
