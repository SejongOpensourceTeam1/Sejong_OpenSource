import "./Header.css";
import { useNavigate } from "react-router-dom";

const Header = () => {
  const nav = useNavigate();

  const onClickLogin = () => {
    nav("/login");
  };

  const onClickSignUp = () => {
    nav("/signup");
  };

  return (
    <div className="header">
      <div className="title">
        <h2 className="logo-title">🎬Reactor</h2>

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

        <button onClick={onClickLogin}>login</button>
        <button onClick={onClickSignUp}>sign in</button>
      </div>
      <hr />
    </div>
  );
};

export default Header;
