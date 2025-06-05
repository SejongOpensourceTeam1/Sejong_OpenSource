import Header from "../components/Header";
import Footer from "../components/Footer";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Mypage.css";

const Mypage = ({
  isLogin,
  setIsLogin,
  setShowLoginModal,
  setShowRegisterModal,
}) => {
  const [myReviews, setMyReviews] = useState([
    {
      id: 0,
      title: "title",
      rating: 0,
      posterPath: "path",
    },
  ]);
  const [userInfo, setUserInfo] = useState({
    nickname: "",
    username: "",
  });
  const navigate = useNavigate();

  const token = localStorage.getItem("accessToken");
  let username = "";
  if (token) {
    const payload = JSON.parse(atob(token.split(".")[1]));
    username = payload.sub;
  }

  useEffect(() => {
    if (!isLogin) {
      const answer = window.confirm(
        "로그인이 필요한 서비스입니다. 로그인하시겠습니까?"
      );
      if (answer) {
        setShowLoginModal(true);
      } else {
        navigate("/");
      }
    }
  }, [isLogin, navigate, setShowLoginModal]);

  // 유저 정보 불러오기
  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const token = localStorage.getItem("accessToken");

        const response = await fetch(
          `${import.meta.env.VITE_BACKEND_API_URL}/api/user/mypage`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "ngrok-skip-browser-warning": "true", // ngrok 자동 막음 방지
            },
          }
        );

        if (!response.ok) throw new Error("사용자 정보 불러오기 실패");

        const user = await response.json();
        setUserInfo(user);
      } catch (error) {
        console.error("사용자 정보 불러오기 오류:", error);
      }
    };

    fetchUserInfo();
  }, []);

  // 리뷰 정보 불러오기
  useEffect(() => {
    const fetchReviews = async () => {
      try {
        const token = localStorage.getItem("accessToken");

        const response = await fetch(
          `${import.meta.env.VITE_BACKEND_API_URL}/movies/reviewed/${username}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "ngrok-skip-browser-warning": "true", // ngrok 자동 막음 방지
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

  console.log(myReviews);

  return (
    <div>
      <Header
        isLogin={isLogin}
        setIsLogin={setIsLogin}
        setShowLoginModal={setShowLoginModal}
        setShowRegisterModal={setShowRegisterModal}
      />
      <div className="mypage-container">
        <div className="user-information">
          <p className="nickname">{userInfo.nickname}</p>
          <p className="username">id : {userInfo.username}</p>
          <p className="review-count">
            리뷰 작성한 영화 수 : {myReviews.length}
          </p>
        </div>
        <h2>📌 내가 리뷰 쓴 영화</h2>
        {myReviews.length === 0 ? (
          <p>아직 작성한 리뷰가 없습니다.</p>
        ) : (
          <div className="movie-list">
            {myReviews.map((review, idx) => (
              <div
                key={idx}
                className="movie-item"
                onClick={() => navigate(`/movie/${review.id}`)}
              >
                <img src={`${review.posterPath}`} alt={review.title} />
                <p>{review.title}</p>
                <p>⭐ 평점: {review.rating}</p>
              </div>
            ))}
          </div>
        )}
      </div>
      <Footer />
    </div>
  );
};

export default Mypage;
