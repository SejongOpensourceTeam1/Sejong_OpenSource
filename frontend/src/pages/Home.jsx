import Header from "../components/Header";
import Context from "../components/Context";
import Login from "../components/Login";
import Register from "../components/Register";
import { useState, useEffect } from "react";

const Home = () => {
  const [isLogin, setIsLogin] = useState(false);
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [showRegisterModal, setShowRegisterModal] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      setIsLogin(true);
    }
  }, []);

  const switchToLogin = () => {
    setShowRegisterModal(false);
    setShowLoginModal(true);
  };

  const switchToRegister = () => {
    setShowLoginModal(false);
    setShowRegisterModal(true);
  };

  return (
    <div>
      <Header
        isLogin={isLogin}
        setIsLogin={setIsLogin}
        setShowLoginModal={setShowLoginModal}
        setShowRegisterModal={setShowRegisterModal}
      />

      {/* 로그인 모달 */}
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

      {/* 회원가입 모달 */}
      {showRegisterModal && (
        <Register
          closeModal={() => setShowRegisterModal(false)}
          switchToLogin={switchToLogin}
        />
      )}

      {/* 메인 콘텐츠 */}
      <Context />
    </div>
  );
};

export default Home;
