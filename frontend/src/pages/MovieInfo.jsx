import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import Header from "../components/Header";
import Footer from "../components/Footer";
import Review from "../components/Review";
import "../styles/MovieInfo.css";

const API_KEY = import.meta.env.VITE_TMDB_API_KEY;

// 영화 상세 정보 페이지
const MovieInfo = ({
  isLogin,
  setIsLogin,
  setShowLoginModal,
  setShowRegisterModal,
}) => {
  const { id } = useParams(); // URL에서 영화 ID 추출
  const [movie, setMovie] = useState(); // 영화 기본 정보
  const [cast, setCast] = useState([]); // 출연진 정보
  const [trailerKey, setTrailerKey] = useState(null); // 예고편 YouTube 키
  const [loading, setLoading] = useState(true); // 로딩 상태

  // 영화 정보, 출연진, 예고편 모두 불러오기
  useEffect(() => {
    const fetchAll = async () => {
      try {
        // 영화 상세 정보
        const res1 = await fetch(
          `https://api.themoviedb.org/3/movie/${id}?api_key=${API_KEY}&language=ko`
        );
        const movieData = await res1.json();
        setMovie(movieData);

        // 출연진 정보 (최대 6명)
        const res2 = await fetch(
          `https://api.themoviedb.org/3/movie/${id}/credits?api_key=${API_KEY}&language=ko`
        );
        const creditsData = await res2.json();
        setCast(creditsData.cast.slice(0, 6));

        // 예고편 (YouTube Trailer만 필터링)
        const res3 = await fetch(
          `https://api.themoviedb.org/3/movie/${id}/videos?api_key=${API_KEY}&language=ko`
        );
        const videoData = await res3.json();
        const trailer = videoData.results.find(
          (v) => v.type === "Trailer" && v.site === "YouTube"
        );
        setTrailerKey(trailer?.key || null);
      } catch (err) {
        console.error("데이터 로딩 실패:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchAll();
  }, [id]);

  // 로딩 중 or 에러 처리
  if (loading) return <div>불러오는 중...</div>;
  if (!movie) return <div>영화를 찾을 수 없습니다.</div>;

  return (
    <div>
      {/* 헤더 */}
      <Header
        isLogin={isLogin}
        setIsLogin={setIsLogin}
        setShowLoginModal={setShowLoginModal}
        setShowRegisterModal={setShowRegisterModal}
      />

      {/* 영화 상세 정보 */}
      <div className="movie-info-container">
        <h1>{movie.title}</h1>

        {/*  포스터 + 예고편: 예고편 유무에 따라 레이아웃 클래스 변경 */}
        <div
          className={`poster-trailer-wrapper ${
            trailerKey ? "with-trailer" : "no-trailer"
          }`}
        >
          <img
            src={`https://image.tmdb.org/t/p/w500${movie.poster_path}`}
            alt={movie.title}
            className="poster"
          />
          {/* 유튜브 예고편 iframe */}
          {trailerKey && (
            <div className="trailer">
              <iframe
                src={`https://www.youtube.com/embed/${trailerKey}`}
                title="예고편"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowFullScreen
              ></iframe>
            </div>
          )}
        </div>

        {/* 영화 설명, 개봉일, 평점 */}
        <p>{movie.overview}</p>
        <p>
          <strong>개봉일:</strong> {movie.release_date}
        </p>
        <p>
          <strong>평점:</strong> {movie.vote_average}
        </p>

        {/* 출연진 목록 */}
        <h2>🎭 주요 출연진</h2>
        <div className="cast-list">
          {cast.map((actor) => (
            <div key={actor.id} className="cast-item">
              <img
                src={
                  actor.profile_path
                    ? `https://image.tmdb.org/t/p/w200${actor.profile_path}`
                    : "https://via.placeholder.com/100x150?text=No+Image"
                }
                alt={actor.name}
              />
              <p>
                <strong>{actor.name}</strong>
              </p>
              <p className="character">({actor.character})</p>
            </div>
          ))}
        </div>
      </div>

      {/* 리뷰 컴포넌트 (props로 영화 ID 전달) */}
      <Review id={id} />

      {/* 푸터 */}
      <Footer />
    </div>
  );
};

export default MovieInfo;
