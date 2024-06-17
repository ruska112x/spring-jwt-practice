import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useDispatch , useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";

import { useFormField } from "../../shared/customHooks/useFormFieldHook";
import paths from "../../shared/paths";
import { login } from "../../shared/store/auth/authAsyncActions";
import { AppDispatch, RootState } from "../../shared/store/store";

import "./SignIn.css";

const SignIn = () => {
  const { t } = useTranslation();
  const dispatch: AppDispatch = useDispatch();
  const auth = useSelector((state: RootState) => state.authReducer);
  const navigate = useNavigate();
  const emailInput = useFormField();
  const passwordInput = useFormField();
  const [loading, setInnerLoading] = useState(false);

  const handleSignIn = async () => {
    setInnerLoading(true);
    await dispatch(login(emailInput.value, passwordInput.value));
    setInnerLoading(false);
    if (auth.error) {
      navigate(paths.signIn);
    } else {
      navigate(paths.index);
    }
  };

  if (loading) {
    return (
      <div className="wrapper">
        <span className="loader"></span>
      </div>
    );
  } else {
    return (
      <div className="sign-in">
        <h1 className="sign-in__header">{t("Sign In")}</h1>
        <form className="sign-in-form">
          <label className="sign-in-label" htmlFor="email">
            {t("Email")}
          </label>
          <input
            className="sign-in-input"
            type="email"
            id="email"
            name="email"
            value={emailInput.value}
            onChange={emailInput.onChange}
          />
          <label className="sign-in-label" htmlFor="password">
            {t("Password")}
          </label>
          <input
            className="sign-in-input"
            type="password"
            id="password"
            name="password"
            value={passwordInput.value}
            onChange={passwordInput.onChange}
          />
          <button
            className="sign-in-button"
            type="button"
            onClick={handleSignIn}
          >
            {t("Continue")}
          </button>
        </form>
        <p className="sign-in-to-register-text">{t("Dont have an account?")}</p>
        <Link className="sign-in-to-register-link" to={paths.signUp}>
          {t("Register!")}
        </Link>
      </div>
    );
  }
};

export default SignIn;
