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
        <h2>영화 리뷰 플랫폼</h2>
        <input placeholder="검색어를 입력하세요" />
        <p onClick={onClickLogin}>로그인</p>
        <button onClick={onClickSignUp}>회원가입</button>
      </div>
      <hr />
    </div>
  );
};

export default Header;
