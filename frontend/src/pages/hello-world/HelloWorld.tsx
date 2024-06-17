import { useDispatch, useSelector } from "react-redux";

import { useFormField } from "../../shared/customHooks/useFormFieldHook";
import { AppDispatch, RootState } from "../../shared/store/store";
import { setLoading, setText } from "../../shared/store/text/textActions";

import "./HelloWorld.css";

const HelloWorld = () => {
  const dispatch: AppDispatch = useDispatch();

  const text = useSelector((state: RootState) => state.textReducer.text);
  const textIsLoading = useSelector(
    (state: RootState) => state.textReducer.isLoading,
  );

  setTimeout(() => {
    dispatch(setLoading(false));
  }, 3000);

  const textInput = useFormField();

  const handleSubmit = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "Enter") {
      dispatch(setText(textInput.value));
      textInput.setValue("");
    }
  };

  if (textIsLoading) {
    return (
      <div className="hello-world">
        <span className="loader"></span>
      </div>
    );
  } else {
    return (
      <div className="hello-world">
        <h1>{text}</h1>
        <input
          type="text"
          onChange={textInput.onChange}
          value={textInput.value}
          onKeyDown={handleSubmit}
        ></input>
      </div>
    );
  }
};

export default HelloWorld;
