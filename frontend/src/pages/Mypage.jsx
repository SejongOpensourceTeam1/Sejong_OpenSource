import Header from "../components/Header";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

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

const Mypage = () => {
  //const nickname = localStorage.getItem("nickname");
  const nickname = "peachgirl";
  const [myReviews, setMyReviews] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const mine = dummyReviews.filter((review) => review.writer === nickname);
    setMyReviews(mine);
  }, [nickname]);

  return (
    <div>
      <Header />
      <div className="mypage-container">
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
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default Mypage;
