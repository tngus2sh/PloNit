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
import storageSession from "redux-persist/lib/storage/session";

import userSlice from "./user-slice";
import windowHeightSlice from "./windowHeight-slice";

const rootReducer = combineReducers({
  user: userSlice,
  windowHeight: windowHeightSlice,
});
const persistConfig = {
  key: "PloNit",
  storage: storage,
  whitelist: [],
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
