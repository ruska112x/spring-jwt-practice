import { initReactI18next } from "react-i18next";
import i18n from "i18next";

const resources = {
  en: {
    translation: {
      "Hello from reducer!": "Hello from reducer!",
      "Home": "Home",
      "Sign Up": "Sign Up",
      "Sign In": "Sign In",
      "Email": "Email",
      "Password": "Password",
      "Continue": "Continue",
      "Dont have an account?": "Dont have an account?",
      "Register!": "Register!",
      "Already have an account?": "Already have an account?",
      "Sign In!": "Sign In!",
      "Logout": "Logout"
    },
  },
  ru: {
    translation: {
      "Hello from reducer!": "Привет из редьюсера",
      "Home": "Главная",
      "Sign Up": "Зарегистрироваться",
      "Sign In": "Войти",
      "Email": "Электронная почта",
      "Password": "Пароль",
      "Continue": "Продолжить",
      "Dont have an account?": "Ещё нет аккаунта?",
      "Register!": "Зарегистрируйтесь!",
      "Already have an account?": "Уже есть аккаунт?",
      "Sign In!": "Войдите!",
      "Logout": "Выйти"
    },
  },
};

i18n.use(initReactI18next).init({
  resources,
  lng: "en",
  interpolation: {
    escapeValue: false,
  },
});

export default i18n;
