import { ThunkAction } from "redux-thunk";

import {
  AuthActionTypes,
  loginFailure,
  loginRequest,
  loginSuccess,
  logoutRequest,
} from "./authActions";
import { RootState } from "../store";

export const login = (
  email: string,
  password: string,
): ThunkAction<void, RootState, unknown, AuthActionTypes> => {
  return async (dispatch) => {
    dispatch(loginRequest());
    const response = await fetch("http://localhost:8080/auth/sign-in", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email: email, password: password }),
      credentials: "include",
    });

    if (!response.ok) {
      dispatch(loginFailure("status isn't ok"));
    } else {
      const data = await response.json();
      dispatch(loginSuccess(data.token));
    }
  };
};

export const signUp = (
  email: string,
  password: string,
): ThunkAction<void, RootState, unknown, AuthActionTypes> => {
  return async (dispatch) => {
    dispatch(loginRequest());
    const response = await fetch("http://localhost:8080/auth/sign-up", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email: email, password: password }),
      credentials: "include",
    });

    if (!response.ok) {
      dispatch(loginFailure("status isn't ok"));
    } else {
      const data = await response.json();
      dispatch(loginSuccess(data.token));
    }
  };
};

export const refresh = (): ThunkAction<
  void,
  RootState,
  unknown,
  AuthActionTypes
> => {
  return async (dispatch) => {
    dispatch(loginRequest());
    const response = await fetch("http://localhost:8080/auth/refresh", {
      method: "POST",
      credentials: "include",
    });

    if (!response.ok) {
      dispatch(loginFailure("status isn't ok"));
    } else {
      const data = await response.json();
      dispatch(loginSuccess(data.token));
    }
  };
};

export const logout = (): ThunkAction<
  void,
  RootState,
  unknown,
  AuthActionTypes
> => {
  return async (dispatch) => {
    dispatch(logoutRequest());
    const response = await fetch("http://localhost:8080/auth/logout", {
      method: "DELETE",
      credentials: "include",
    });

    if (!response.ok) {
      dispatch(loginFailure("status isn't ok"));
    }
  };
};
