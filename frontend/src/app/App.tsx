import { Provider } from "react-redux";

import MainRouter from "./MainRouter";
import store from "../shared/store/store";

import "../shared/translation"

const App = () => {
  return (
    <Provider store={store}>
      <MainRouter />
    </Provider>
  )
}

export default App;
