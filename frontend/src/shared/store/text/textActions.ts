export const GET_TEXT = "GET_TEXT";
export const SET_TEXT = "SET_TEXT";
export const SET_LOADING = "SET_LOADING";

interface GetTextAction {
  type: typeof GET_TEXT;
}

interface SetTextAction {
  type: typeof SET_TEXT;
  payload: {
    text: string;
  };
}

interface SetTextLoadingAction {
  type: typeof SET_LOADING;
  payload: {
    flag: boolean;
  };
}

export type TextActionTypes =
  | GetTextAction
  | SetTextAction
  | SetTextLoadingAction;

export const getText = (): TextActionTypes => {
  return {
    type: GET_TEXT,
  };
};

export const setText = (text: string): TextActionTypes => {
  return {
    type: SET_TEXT,
    payload: { text: text },
  };
};

export const setLoading = (flag: boolean): TextActionTypes => {
  return {
    type: SET_LOADING,
    payload: { flag: flag },
  };
};
