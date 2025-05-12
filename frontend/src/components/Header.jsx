import "./Header.css";
import { useNavigate } from "react-router-dom";

const Header = ({
  isLogin,
  setIsLogin,
  setShowLoginModal,
  setShowRegisterModal,
}) => {
  const nav = useNavigate();

  const logout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("nickname");
    setIsLogin(false);
  };

  return (
    <div className="header">
      <div className="title">
        <h2 className="logo-title" onClick={() => nav("/")}>
          🎬Reactor
        </h2>

        <div className="search-box">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            className="search-icon"
            viewBox="0 0 24 24"
            width="18"
            height="18"
            fill="#888"
          >
            <path d="M10 2a8 8 0 105.293 14.293l5.707 5.707 1.414-1.414-5.707-5.707A8 8 0 0010 2zm0 2a6 6 0 110 12 6 6 0 010-12z" />
          </svg>
          <input type="text" placeholder="영화 제목 검색" />
        </div>

        <div className="auth-buttons">
          {isLogin ? (
            <>
              <button onClick={logout}>로그아웃</button>
              <button onClick={() => nav("/mypage")}>마이페이지</button>
            </>
          ) : (
            <>
              <button onClick={() => setShowLoginModal(true)}>로그인</button>
              <button onClick={() => setShowRegisterModal(true)}>
                회원가입
              </button>
            </>
          )}
        </div>
      </div>
      <hr />
    </div>
  );
};

export default Header;
