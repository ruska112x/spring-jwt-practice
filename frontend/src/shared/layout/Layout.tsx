import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useDispatch, useSelector } from "react-redux";
import { Link, Navigate, Outlet } from "react-router-dom";

import { setLoading } from "../../shared/store/text/textActions";
import paths from "../paths";
import { logout,refresh } from "../store/auth/authAsyncActions";
import { AppDispatch, RootState } from "../store/store";

import "./Layout.css";

const Layout = () => {
  const { t } = useTranslation();
  const auth = useSelector((state: RootState) => state.authReducer);
  const [loading, setInnerLoading] = useState(true);
  const dispatch: AppDispatch = useDispatch();

  const handleLogout = () => {
    dispatch(logout());
    dispatch(setLoading(true));
  };

  useEffect(() => {
    dispatch(refresh());
    setInnerLoading(false);
  }, []);

  if (auth.accessToken) {
    return (
      <div className="layout">
        <header className="layout__header">
          <Link className="layout__header-home" to={paths.index}>
            {t("Home")}
          </Link>
          <Link
            onClick={handleLogout}
            className="layout__header-logout"
            to={paths.signIn}
          >
            {t("Logout")}
          </Link>
        </header>
        <main className="layout__main">
          <Outlet />
        </main>
      </div>
    );
  } else {
    if (auth.isLoading || loading) {
      return (
        <div className="layout">
          <main className="layout__main">
            <div className="wrapper">
              <span className="loader"></span>
            </div>
          </main>
        </div>
      );
    } else {
      return <Navigate to={paths.signUp} />;
    }
  }
};

export default Layout;
