import Header from "../components/Header";
import Footer from "../components/Footer";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Mypage.css";

// 마이페이지 컴포넌트
const Mypage = ({
  isLogin,
  setIsLogin,
  setShowLoginModal,
  setShowRegisterModal,
}) => {
  // 내가 작성한 리뷰 상태
  const [myReviews, setMyReviews] = useState([
    {
      id: 0,
      title: "title",
      rating: 0,
      posterPath: "path",
    },
  ]);

  // 사용자 정보 상태
  const [userInfo, setUserInfo] = useState({
    nickname: "",
    username: "",
  });

  const navigate = useNavigate();

  // 토큰에서 username 추출
  const token = localStorage.getItem("accessToken");
  let username = "";
  if (token) {
    const payload = JSON.parse(atob(token.split(".")[1]));
    username = payload.sub;
  }

  // 로그인 안 된 경우 알림 및 리다이렉트
  useEffect(() => {
    if (!isLogin) {
      const answer = window.confirm(
        "로그인이 필요한 서비스입니다. 로그인하시겠습니까?"
      );
      if (answer) {
        setShowLoginModal(true); // 로그인 모달 열기
      } else {
        navigate("/"); // 홈으로 이동
      }
    }
  }, [isLogin, navigate, setShowLoginModal]);

  // 👤 사용자 정보 불러오기
  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const token = localStorage.getItem("accessToken");

        const response = await fetch(
          `${import.meta.env.VITE_BACKEND_API_URL}/api/user/mypage`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "ngrok-skip-browser-warning": "true", // ngrok 경고 방지
            },
          }
        );

        if (!response.ok) throw new Error("사용자 정보 불러오기 실패");

        const user = await response.json();
        setUserInfo(user); // 상태 저장
      } catch (error) {
        console.error("사용자 정보 불러오기 오류:", error);
      }
    };

    fetchUserInfo();
  }, []);

  // 📝 내가 작성한 리뷰 불러오기
  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const token = localStorage.getItem("accessToken");

        const response = await fetch(
          `${import.meta.env.VITE_BACKEND_API_URL}/movies/reviewed/${username}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "ngrok-skip-browser-warning": "true",
            },
          }
        );

        if (!response.ok) throw new Error("리뷰 정보 불러오기 실패");

        const reviews = await response.json();
        setMyReviews(reviews);
      } catch (error) {
        console.error("리뷰 정보 불러오기 오류:", error);
      }
    };

    fetchReviews();
  }, [username]);

  console.log(myReviews); // 디버깅용 로그

  return (
    <div>
      {/* 상단 헤더 */}
      <Header
        isLogin={isLogin}
        setIsLogin={setIsLogin}
        setShowLoginModal={setShowLoginModal}
        setShowRegisterModal={setShowRegisterModal}
      />

      {/* 마이페이지 본문 */}
      <div className="mypage-container">
        {/* 유저 정보 */}
        <div className="user-information">
          <p className="nickname">{userInfo.nickname}</p>
          <p className="username">id : {userInfo.username}</p>
          <p className="review-count">
            리뷰 작성한 영화 수 : {myReviews.length}
          </p>
        </div>

        <h2>📌 내가 리뷰 쓴 영화</h2>

        {/* 리뷰 없을 때 안내문 / 있을 때 영화 목록 출력 */}
        {myReviews.length === 0 ? (
          <p>아직 작성한 리뷰가 없습니다.</p>
        ) : (
          <div className="movie-list">
            {myReviews.map((review, idx) => (
              <div
                key={idx}
                className="movie-item"
                onClick={() => navigate(`/movie/${review.id}`)} // 영화 상세 이동
              >
                <img src={`${review.posterPath}`} alt={review.title} />
                <p>{review.title}</p>
                <p>⭐ 평점: {review.rating}</p>
              </div>
            ))}
          </div>
        )}
      </div>

      {/* 하단 푸터 */}
      <Footer />
    </div>
  );
};

export default Mypage;
