import { BrowserRouter, Route, Routes } from "react-router-dom";

import HelloWorld from "../pages/hello-world/HelloWorld";
import SignIn from "../pages/sign-in/SignIn";
import SignUp from "../pages/sign-up/SignUp";
import Layout from "../shared/layout/Layout";
import paths from "../shared/paths";

const MainRouter = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path={paths.signUp} element={<SignUp />} />
        <Route path={paths.signIn} element={<SignIn />} />
        <Route path={paths.index} element={<Layout />}>
          <Route index element={<HelloWorld />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

export default MainRouter;
