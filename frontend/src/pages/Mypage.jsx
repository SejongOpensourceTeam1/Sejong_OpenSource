import Header from "../components/Header";
import Footer from "../components/Footer";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Mypage.css";

// 임시로 로컬 리뷰 데이터 (실제 앱에서는 서버나 context에서 받아옴)
const dummyReviews = [
  {
    movieId: "550",
    movieTitle: "파이트 클럽",
    posterPath: "/bptfVGEQuv6vDTIMVCHjJ9Dz8PX.jpg",
    writer: "peachgirl",
    content: "인생 영화!",
  },
  {
    movieId: "27205",
    movieTitle: "인셉션",
    posterPath: "/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg",
    writer: "peachgirl",
    content: "복잡한데 재밌어!",
  },
  {
    movieId: "155",
    movieTitle: "다크 나이트",
    posterPath: "/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
    writer: "peachgirl",
    content: "조커 전설",
  },
];

const Mypage = ({
  isLogin,
  setIsLogin,
  setShowLoginModal,
  setShowRegisterModal,
}) => {
  // const nickname = "peachgirl";
  const [myReviews, setMyReviews] = useState([dummyReviews]);
  const [userInfo, setUserInfo] = useState({
    nickname: "",
    username: "",
  });
  const navigate = useNavigate();

  // useEffect(() => {
  //   const mine = dummyReviews.filter((review) => review.writer === nickname);
  //   setMyReviews(mine);
  // }, [nickname]);

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
              "ngrok-skip-browser-warning": "true", // ✅ 요거 추가!
            },
          }
        );

        if (!response.ok) throw new Error("사용자 정보 불러오기 실패");

        const user = await response.json();
        setUserInfo(user);
      } catch (error) {
        console.error("사용자자 정보 불러오기 오류:", error);
      }
    };

    fetchUserInfo();
  }, []);

  // ✅ 리뷰 쓴 영화 목록 불러오기
  useEffect(() => {
    const fetchReviewMovies = async () => {
      try {
        const token = localStorage.getItem("accessToken");

        const response = await fetch(
          `${import.meta.env.VITE_BACKEND_API_URL}/mypage`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              "ngrok-skip-browser-warning": "true", // ✅ 요거 추가!
            },
          }
        );

        if (!response.ok) throw new Error("리뷰 작성한 영화 불러오기 실패");

        const reviews = await response.json();
        console.log(reviews);

        // ✅ 각 리뷰에 대해 영화 정보 요청
        const enrichedReviews = await Promise.all(
          reviews.map(async (review) => {
            const movieRes = await fetch(
              `https://api.themoviedb.org/3/movie/${review.movieId}?api_key=${
                import.meta.env.VITE_TMDB_API_KEY
              }&language=ko`
            );
            const movie = await movieRes.json();

            return {
              ...review,
              movieTitle: movie.title,
              posterPath: movie.poster_path,
              voteAverage: movie.vote_average, // 평점
            };
          })
        );

        setMyReviews(enrichedReviews);
      } catch (error) {
        console.error("리뷰 작성한 영화 불러오기 오류:", error);
      }
    };

    fetchReviewMovies();
  }, []);

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
          <p>닉네임 : {userInfo.nickname}</p>
          <p>아이디 : {userInfo.username}</p>
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
                onClick={() => navigate(`/movie/${review.movieId}`)}
              >
                <img
                  src={`https://image.tmdb.org/t/p/w200${review.posterPath}`}
                  alt={review.movieTitle}
                />
                <p>{review.movieTitle}</p>
                <small>"{review.content}"</small>
                <p>⭐ 평점: {review.voteAverage}</p>
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
