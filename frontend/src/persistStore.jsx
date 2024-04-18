import { configureStore, current } from "@reduxjs/toolkit";
import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";




export const initialState = {
  member: { memberNo: "" },
  accessToken: "",
  vaildationTest: false
};



const reducer = (currentState, action) => {
  if (currentState === undefined) {
    return initialState;
  }
  const newState = { ...currentState };
  switch (action.type) {
    case "member":
      newState.member = action.payload;
      break;
    case "accessToken":
      newState.accessToken = action.payload;
      break;
    default:
  }
  return newState;
};

const persistConfig = {
  key: "root",
  storage,
};

const persistedReducer = persistReducer(persistConfig, reducer);
const store = configureStore({
  reducer: { persistedReducer },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});

export default store;
