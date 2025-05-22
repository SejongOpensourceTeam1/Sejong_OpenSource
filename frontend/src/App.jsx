import "./App.css";
import { Routes, Route } from "react-router-dom";
import { useState } from "react";
import Home from "./pages/Home";
import Mypage from "./pages/Mypage";
import MovieInfo from "./pages/MovieInfo";
import Login from "./components/Login";
import Register from "./components/Register";

function App() {
  const [isLogin, setIsLogin] = useState(localStorage.getItem("accessToken"));
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [showRegisterModal, setShowRegisterModal] = useState(false);

  const switchToLogin = () => {
    setShowRegisterModal(false);
    setShowLoginModal(true);
  };

  const switchToRegister = () => {
    setShowLoginModal(false);
    setShowRegisterModal(true);
  };
  return (
    <>
      {showLoginModal && (
        <Login
          closeModal={() => setShowLoginModal(false)}
          onLoginSuccess={() => {
            setIsLogin(true);
            setShowLoginModal(false);
          }}
          switchToRegister={switchToRegister}
        />
      )}
      {showRegisterModal && (
        <Register
          closeModal={() => setShowRegisterModal(false)}
          switchToLogin={switchToLogin}
        />
      )}

      <Routes>
        <Route
          path="/"
          element={
            <Home
              isLogin={isLogin}
              setIsLogin={setIsLogin}
              setShowLoginModal={setShowLoginModal}
              setShowRegisterModal={setShowRegisterModal}
            />
          }
        />
        <Route
          path="/mypage"
          element={
            <Mypage
              isLogin={isLogin}
              setIsLogin={setIsLogin}
              setShowLoginModal={setShowLoginModal}
              setShowRegisterModal={setShowRegisterModal}
            />
          }
        />
        <Route
          path="/movie/:id"
          element={
            <MovieInfo
              isLogin={isLogin}
              setIsLogin={setIsLogin}
              setShowLoginModal={setShowLoginModal}
              setShowRegisterModal={setShowRegisterModal}
            />
          }
        />
      </Routes>
    </>
  );
}

export default App;
