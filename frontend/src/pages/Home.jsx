import Header from "../components/Header";
import Footer from "../components/Footer";
import Context from "../components/Context";
import { useState, useCallback } from "react";

// 홈 페이지 컴포넌트
const Home = ({
  isLogin,              // 로그인 상태
  setIsLogin,           // 로그인 상태 변경 함수
  setShowLoginModal,    // 로그인 모달 표시 함수
  setShowRegisterModal, // 회원가입 모달 표시 함수
}) => {
  const [searchKeyword, setSearchKeyword] = useState(""); // 검색어 상태

  // 검색어 변경 콜백 (Header → Context 전달용)
  const handleSearch = useCallback((keyword) => {
    setSearchKeyword(keyword);
  }, []);

  return (
    <div>
      {/* 상단 헤더 */}
      <Header
        isLogin={isLogin}
        setIsLogin={setIsLogin}
        setShowLoginModal={setShowLoginModal}
        setShowRegisterModal={setShowRegisterModal}
        onSearch={handleSearch} // 검색 키워드 전달 함수
      />

      {/* 메인 콘텐츠 영역 (검색 키워드 전달됨) */}
      <Context searchKeyword={searchKeyword} />

      {/* 하단 푸터 */}
      <Footer />
    </div>
  );
};

export default Home;
