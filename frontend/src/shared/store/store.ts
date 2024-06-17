import { applyMiddleware, combineReducers, legacy_createStore as createStore } from "redux";
import { thunk,ThunkMiddleware } from "redux-thunk";

import { AuthActionTypes } from "./auth/authActions";
import authReducer from "./auth/authReducer";
import textReducer from "./text/textReducer";

const rootReducer = combineReducers({
  textReducer,
  authReducer
});

const store = createStore(rootReducer, {}, applyMiddleware(thunk as ThunkMiddleware<RootState, AuthActionTypes>));

export type RootState = ReturnType<typeof rootReducer>;

export default store;

export type AppDispatch = typeof store.dispatch;
