import Header from "../components/Header";
import Footer from "../components/Footer";
import Context from "../components/Context";
import { useState, useCallback } from "react";

const Home = ({
  isLogin,
  setIsLogin,
  setShowLoginModal,
  setShowRegisterModal,
}) => {
  const [searchKeyword, setSearchKeyword] = useState("");

  const handleSearch = useCallback((keyword) => {
    setSearchKeyword(keyword);
  }, []);

  return (
    <div>
      <Header
        isLogin={isLogin}
        setIsLogin={setIsLogin}
        setShowLoginModal={setShowLoginModal}
        setShowRegisterModal={setShowRegisterModal}
        onSearch={handleSearch}
      />
      <Context searchKeyword={searchKeyword} />
      <Footer />
    </div>
  );
};

export default Home;
