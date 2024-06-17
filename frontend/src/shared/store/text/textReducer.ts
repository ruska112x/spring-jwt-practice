import {
  GET_TEXT,
  SET_LOADING,
  SET_TEXT,
  TextActionTypes,
} from "./textActions";

type TextState = {
  isLoading: boolean;
  text: string;
};

const initialState: TextState = {
  isLoading: true,
  text: "Hello, from reducer!",
};

const textReducer = (state = initialState, action: TextActionTypes) => {
  switch (action.type) {
    case GET_TEXT:
      return {
        ...state,
        isLoading: true,
      };
    case SET_TEXT:
      return {
        ...state,
        text: action.payload.text,
      };
    case SET_LOADING:
      return {
        ...state,
        isLoading: action.payload.flag,
      };
    default:
      return state;
  }
};

export default textReducer;
