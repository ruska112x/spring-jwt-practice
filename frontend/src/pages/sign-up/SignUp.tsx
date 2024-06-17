import { useState } from "react";
import { useTranslation } from "react-i18next";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";

import { useFormField } from "../../shared/customHooks/useFormFieldHook";
import paths from "../../shared/paths";
import { signUp } from "../../shared/store/auth/authAsyncActions";
import { AppDispatch, RootState } from "../../shared/store/store";

import "./SignUp.css";

const SignUp = () => {
  const { t } = useTranslation();
  const dispatch: AppDispatch = useDispatch();
  const navigate = useNavigate();
  const emailInput = useFormField();
  const passwordInput = useFormField();
  const [loading, setInnerLoading] = useState(false);
  const auth = useSelector((state: RootState) => state.authReducer);

  const handleSignUp = async () => {
    setInnerLoading(true);
    await dispatch(signUp(emailInput.value, passwordInput.value));
    setInnerLoading(false);
    if (auth.error) {
      navigate(paths.signUp);
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
      <div className="sign-up">
        <h1 className="sign-up__header">{t("Sign Up")}</h1>
        <form className="sign-up-form">
          <label className="sign-up-label" htmlFor="email">
            {t("Email")}
          </label>
          <input
            className="sign-up-input"
            type="email"
            id="email"
            name="email"
            value={emailInput.value}
            onChange={emailInput.onChange}
          />
          <label className="sign-up-label" htmlFor="password">
            {t("Password")}
          </label>
          <input
            className="sign-up-input"
            type="password"
            id="password"
            name="password"
            value={passwordInput.value}
            onChange={passwordInput.onChange}
          />
          <button
            className="sign-up-button"
            type="button"
            onClick={handleSignUp}
          >
            {t("Continue")}
          </button>
        </form>
        <p className="sign-up-to-register-text">
          {t("Already have an account?")}
        </p>
        <Link className="sign-up-to-register-link" to={paths.signIn}>
          {t("Sign In!")}
        </Link>
      </div>
    );
  }
};

export default SignUp;
