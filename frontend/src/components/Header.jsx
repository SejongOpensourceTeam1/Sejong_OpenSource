import "./Header.css";
import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

const Header = ({
  isLogin,               // 로그인 여부 (true/false)
  setIsLogin,            // 로그인 상태 변경 함수
  setShowLoginModal,     // 로그인 모달 표시 함수
  setShowRegisterModal,  // 회원가입 모달 표시 함수
  onSearch,              // 검색 입력 콜백 함수
}) => {
  const [input, setInput] = useState(""); // 검색 입력 상태
  const nav = useNavigate(); // 페이지 이동용 훅

  // 로그아웃 함수
  const logout = () => {
    localStorage.removeItem("accessToken"); // 토큰 삭제
    setIsLogin(false); // 로그인 상태 false로
    nav("/"); // 홈으로 이동
    window.location.reload(); // 새로고침 (상태 초기화)
  };

  // 검색 입력 감지 (0.1초 디바운스)
  useEffect(() => {
    const timer = setTimeout(() => {
      if (typeof onSearch === "function") {
        onSearch(input.trim()); // 공백 제거 후 검색 실행
      }
    }, 100);

    return () => clearTimeout(timer); // 입력 변경 시 타이머 초기화
  }, [input, onSearch]);

  return (
    <div className="header">
      <div className="title">
        {/* 로고 클릭 시 홈으로 이동 */}
        <h2 className="logo-title" onClick={() => nav("/")}>
          🎬CineCampus
        </h2>

        {/* 검색창 영역 */}
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
          <input
            type="text"
            placeholder="영화 제목 검색"
            value={input}
            onChange={(e) => setInput(e.target.value)} // 입력 값 상태 저장
          />
        </div>

        {/* 로그인 여부에 따라 버튼 분기 */}
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
      {/* 구분선 */}
      <hr />
    </div>
  );
};

export default Header;
